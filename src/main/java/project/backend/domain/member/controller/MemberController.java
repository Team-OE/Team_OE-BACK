package project.backend.domain.member.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.util.ObjectUtils;
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

    @ApiOperation(
            value = "회원 정보 얻기",
            notes = "AccessToken 또는 memberId로 회원 정보를 얻는다.")
    @GetMapping("/{memberId}")
    public ResponseEntity getMember(
            @RequestHeader(value = "Authorization", required = false) String accessToken,
            @PathVariable(required = false) Long memberId) {

        if (ObjectUtils.isEmpty(accessToken) && ObjectUtils.isEmpty(memberId)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }

        if(!ObjectUtils.isEmpty(accessToken)) {
            memberId = jwtService.getMemberFromAccessToken(accessToken).getId();
        }

        MemberResponseDto memberResponseDto = memberMapper.memberToMemberResponseDto(memberService.getMember(memberId));
        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);
    }

    @ApiOperation(
            value = "회원가입 & 로그인",
            notes = " - request : {\"socialId\" :\"abcde2\", \"socialType\":\"KAKAO\"}\n" +
                    "1. socialId, socialType은 필수\n" +
                    "2. socialType은 \"KAKAO\", \"APPLE\", \"GOOGLE\"만 가능합니다.")
    @PostMapping("/login")
    public ResponseEntity login(
            @Valid @RequestBody MemberPostRequestDto request) {

        // socialId, socialType기준 Member 반환, 없다면 새로 생성
        Member member = memberService.getMemberBySocial(request.socialId, request.socialType);

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


    @ApiOperation(
            value = "Member 조회 & 닉네임 조회(중복 검사)",
            notes = "1. AccessToken으로 조회할 경우 : Header의 Authorization에 accessToken을 넣어주세요.\n" +
                    "2. socialId와 socialType으로 조회할 경우 : ?social-id=abcdefg&social-type=KAKAO\n" +
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

    @ApiIgnore
    @ApiOperation(value = "리프레시 토큰 수정")
    @RequestMapping(method = RequestMethod.PATCH, consumes = "multipart/form-data")
    public ResponseEntity patchMember(
            @RequestHeader("Authorization") String accessToken,
            @Valid @RequestBody MemberPatchRequestDto request) {
        Member member = jwtService.getMemberFromAccessToken(accessToken);
        if (request == null) {
            request = MemberPatchRequestDto.builder().build();
        }
        MemberResponseDto memberResponseDto = memberMapper.memberToMemberResponseDto(memberService.patchMember(member.getId(), request));
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
