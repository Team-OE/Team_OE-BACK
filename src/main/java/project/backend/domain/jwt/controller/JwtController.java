package project.backend.domain.jwt.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.backend.domain.jwt.dto.JwtRequestDto;
import project.backend.domain.jwt.response.JwtResponse;
import project.backend.domain.jwt.response.TokenResponse;
import project.backend.domain.jwt.service.JwtService;
import project.backend.domain.member.dto.MemberPatchRequestDto;
import project.backend.domain.member.dto.MemberResponseDto;
import project.backend.domain.member.entity.Member;
import project.backend.domain.member.mapper.MemberMapper;
import project.backend.domain.member.service.MemberService;
import project.backend.global.s3.service.ImageService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@Api(tags = "로그인 API")
@Validated
@AllArgsConstructor
@Slf4j
public class JwtController {

    private final MemberService memberService;
    private final JwtService jwtService;
    private final MemberMapper memberMapper;


    @ApiOperation(
            value = "회원가입 & 로그인",
            notes = " - request : {\"socialId\" :\"abcde2\", \"socialType\":\"KAKAO\"}\n" +
                    "1. socialId, socialType은 필수\n" +
                    "2. socialType은 \"KAKAO\", \"APPLE\", \"GOOGLE\"만 가능합니다.")
    @PostMapping("/login")
    public ResponseEntity login(
            @Valid @RequestBody JwtRequestDto request) {

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
}