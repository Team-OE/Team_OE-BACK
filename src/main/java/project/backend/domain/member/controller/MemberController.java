package project.backend.domain.member.controller;

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

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Api(tags = "멤버 API")
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final JwtService jwtService;
    private final LogoutTokenService logoutTokenService;

    @GetMapping("/{memberId}")
    public ResponseEntity getMember(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long memberId) {
        MemberResponseDto memberResponseDto = memberMapper.memberToMemberResponseDto(memberService.getMember(memberId));
        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);
    }


    @ApiOperation(
            value = "Member 조회 & 닉네임 조회(중복 검사)",
            notes = "1. AccessToken으로 조회할 경우 : Header의 Authorization에 accessToken을 넣어주세요.\n" +
                    "2. socialId와 socialType으로 조회할 경우 : ?socialId=abcdefg&socialType=KAKAO\n" +
                    "" +
                    " - 해당 Member 없을 경우 -> 400에러, code : U001, message : 사용자를 찾을 수 없습니다.\n" +
                    " - socialType은 KAKAO와 APPLE만 가능합니다.")
    @GetMapping
    public ResponseEntity getMember(
            @RequestHeader(value = "Authorization", required = false) String accessToken,
            @RequestParam(required = false) String socialId,
            @RequestParam(required = false) SocialType socialType) {

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

    @GetMapping("/list")
    public ResponseEntity getMemberList(
            @RequestHeader("Authorization") String accessToken) {
        List<MemberResponseDto> memberResponseDtoList = memberMapper.membersToMemberResponseDtos(memberService.getMemberList());
        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDtoList);
    }

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
