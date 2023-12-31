package project.backend.domain.member.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import project.backend.domain.jwt.response.JwtResponse;
import project.backend.domain.jwt.response.TokenResponse;
import project.backend.domain.jwt.service.JwtService;
import project.backend.domain.member.dto.*;
import project.backend.domain.member.entity.Member;
import project.backend.domain.member.entity.SocialType;
import project.backend.domain.member.mapper.MemberMapper;
import project.backend.domain.member.service.LogoutTokenService;
import project.backend.domain.member.service.MemberService;
import project.backend.global.error.exception.BusinessException;
import project.backend.global.error.exception.ErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.backend.global.s3.service.ImageService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Api(tags = "멤버 API - 완료 API(프론트 작업 가능)")
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final JwtService jwtService;
    private final LogoutTokenService logoutTokenService;
    private final ImageService imageService;

    @ApiOperation(
            value = "회원 정보 얻기",
            notes = "AccessToken 또는 memberId로 회원 정보를 얻는다.")
    @GetMapping("/{memberId}")
    public ResponseEntity getMember(
            @RequestHeader(value = "Authorization", required = false) String accessToken,
            @PathVariable(required = false) Long memberId) {

        if (ObjectUtils.isEmpty(accessToken) && ObjectUtils.isEmpty(memberId)) {
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }

        if (!ObjectUtils.isEmpty(accessToken)) {
            memberId = jwtService.getMemberFromAccessToken(accessToken).getId();
        }

        MemberResponseDto memberResponseDto = memberMapper.memberToMemberResponseDto(memberService.getMember(memberId));
        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);
    }

    @ApiOperation(
            value = "회원가입 & 로그인",
            notes = " - social-type : KAKAO/GOOGLE/NAVER(필수)\n" +
                    " - code : 인가 코드(필수)\n" +
                    " - redirect-url : http://localhost:3000/login/(필수) 프론트가 인가코드 요청한 주소, KAKAO Developers에 등록 요망")
    @GetMapping("/login")
    public ResponseEntity login(
            @RequestParam(value = "social-type", required = false) String socialType,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "redirect-url", required = false) String redirectUrl) {

        if (ObjectUtils.isEmpty(socialType) || ObjectUtils.isEmpty(code) || ObjectUtils.isEmpty(redirectUrl)) {
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        String socialId = jwtService.getKakaoUserInfo(jwtService.getKakaoAccessToken(code, redirectUrl));

        // socialId, socialType기준 Member 반환, 없다면 새로 생성
        Member member = memberService.getMemberBySocial(socialId, SocialType.KAKAO);

        // accessToken과 refreshToken 발급
        String accessToken = jwtService.getAccessToken(member);
        String refreshToken = member.getRefreshToken();

        // 응답
        MemberResponseDto memberResponse = memberMapper.memberToMemberResponseDto(member);
        TokenResponse tokenResponse = TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken).build();
        JwtResponse jwtResponse = JwtResponse.builder()
                .token(tokenResponse)
                .member(memberResponse).build();
        return new ResponseEntity<>(jwtResponse, HttpStatus.CREATED);
    }

    @ApiIgnore
    @ApiOperation(
            value = "Member 조회",
            notes = "1. AccessToken으로 조회할 경우 : Header의 Authorization에 accessToken을 넣어주세요.\n" +
                    "2. social-id와 social-type으로 조회할 경우 : ?social-id=abcdefg&social-type=KAKAO\n" +
                    "" +
                    " - 해당 Member 없을 경우 -> 400에러, code : U001, message : 사용자를 찾을 수 없습니다.\n" +
                    " - socialType은 KAKAO, APPLE, GOOGLE만 가능합니다.")
    @GetMapping
    public ResponseEntity getMember(
            @RequestHeader(value = "Authorization", required = false) String accessToken,
            @RequestParam(value = "social-id", required = false) String socialId,
            @RequestParam(value = "social-type", required = false) SocialType socialType) {

        System.out.println(accessToken);
        Member member;
        if (accessToken != null) {
            member = jwtService.getMemberFromAccessToken(accessToken);
        } else if (socialId != null && socialType != null) {
            member = memberService.getMemberBySocialIdAndSocialType(socialId, socialType);
        } else {
            throw new BusinessException(ErrorCode.INVALID_REQUEST);
        }
        MemberResponseDto memberResponseDto = memberMapper.memberToMemberResponseDto(member);
        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);
    }

    @ApiIgnore
    @GetMapping("/list")
    public ResponseEntity getMemberList(
            @RequestHeader("Authorization") String accessToken) {
        List<MemberResponseDto> memberResponseDtoList = memberMapper.membersToMemberResponseDtos(memberService.getMemberList());
        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDtoList);
    }

    @ApiOperation(value = "닉네임 검증",
            notes = "- 중복 확인\n" +
                    "- 1자 ~ 10자 이내인지 확인")
    @GetMapping("/verify")
    public ResponseEntity verifyNickname(
            @RequestParam(value = "nickname", required = false) String nickname) {
        memberService.verifiedNickname(nickname);
        return ResponseEntity.status(HttpStatus.OK).body(NicknameResponseDto.builder().nickname(nickname).message(nickname + " 은 사용 가능합니다.").build());
    }

    @ApiOperation(value = "닉네임 & 프로필 이미지 수정",
            notes = "Authorization만 필수")
    @RequestMapping(method = RequestMethod.PATCH, consumes = "multipart/form-data")
    public ResponseEntity patchMember(
            @RequestHeader(value = "Authorization", required = false) String accessToken,
            @RequestPart(value = "nickname", required = false) String nickname,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        if (ObjectUtils.isEmpty(accessToken)) {
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }

        Member member = jwtService.getMemberFromAccessToken(accessToken);

        if (!ObjectUtils.isEmpty(nickname)) {
            memberService.patchMember(member.getId(), MemberPatchRequestDto.builder().nickname(nickname).build());
        }
        if (!ObjectUtils.isEmpty(profileImage)) {
            memberService.patchMember(member.getId(),
                    MemberPatchRequestDto
                            .builder()
                            .profileUrl(imageService.updateImage(profileImage, "Member", "profileUrl"))
                            .build()
            );
        }
        MemberResponseDto memberResponseDto = memberMapper.memberToMemberResponseDto(member);
        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);
    }

    @ApiOperation(value = "회원 탈퇴")
    @DeleteMapping
    public ResponseEntity deleteMember(
            @RequestHeader("Authorization") String accessToken) {
        memberService.deleteMember(jwtService.getMemberFromAccessToken(accessToken).getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @ApiOperation(value = "로그아웃")
    @GetMapping("/logout")
    public ResponseEntity logoutMember(
            @RequestHeader("Authorization") String accessToken) {
        logoutTokenService.memberLogout(accessToken);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
