package project.backend.domain.jwt.service;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.backend.domain.jwt.dto.KakaoUserInfo;
import project.backend.domain.jwt.provider.JwtProvider;
import project.backend.domain.member.dto.MemberPatchRequestDto;
import project.backend.domain.member.entity.Member;
import project.backend.domain.member.repository.LogoutTokenRepository;
import project.backend.domain.member.service.MemberService;
import project.backend.global.error.exception.BusinessException;
import project.backend.global.error.exception.ErrorCode;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class JwtService {

    private final MemberService memberService;
    private final LogoutTokenRepository logoutTokenRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.kakao.client_id}")
    private String client_id;

    public String getAccessToken(Member member){

        // accessToken, refreshToken 발급
        String accessToken = "Bearer " + JwtProvider.createAccessToken(member.getId(), secretKey);
        String refreshToken = "Bearer " + JwtProvider.createRefreshToken(member.getId(), secretKey);

        // member 엔티티에 refreshToken 저장
        memberService.patchMember(member.getId(), MemberPatchRequestDto.builder().refreshToken(refreshToken).build());

        return "Bearer " + JwtProvider.createAccessToken(member.getId(), secretKey);
    }

    public Member getMemberFromAccessToken(String accessToken) {
        if (logoutTokenRepository.findAllByToken(accessToken).size() > 0) {
            throw new BusinessException(ErrorCode.MEMBER_LOGOUT);
        }
        accessToken = accessToken.split(" ")[1];
        Long userId = JwtProvider.getUserId(accessToken, secretKey);
        return memberService.verifiedMember(userId);
    }

    public String getKakaoAccessToken(String code, String redirectUrl) {

        String accessToken = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // POST 요청을 위해 기본값이 false인 setDoOutput을 true로 설정
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            // POST 요처에 필요로 요구하는 파라미터를 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter((new OutputStreamWriter(conn.getOutputStream())));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + client_id);
            sb.append("&redirect_uri=" + redirectUrl);
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            // 결과 코드가 200이라면 성공, 아니면 에러 발생
            if(conn.getResponseCode() != 200) {
                throw new BusinessException(ErrorCode.KAKAO_CODE_NOT_VALID);
            }

            // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            String result = getRequestResult(conn);

            // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonElement element = new Gson().fromJson(result, JsonElement.class);
            accessToken = element.getAsJsonObject().get("access_token").getAsString();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accessToken;
    }


    public String getKakaoUserInfo(String token) {
        log.info("token : " + token);

        String reqURL = "https://kapi.kakao.com/v2/user/me";
        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo();


        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new BusinessException(ErrorCode.KAKAO_CODE_NOT_VALID);
            }
            log.info("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            String result = getRequestResult(conn);

            //Gson 라이브러리로 JSON파싱
            JsonElement element = new Gson().fromJson(result, JsonElement.class);

            //socialId
            return element.getAsJsonObject().get("id").getAsString();

        } catch (IOException e) {
            throw new BusinessException(ErrorCode.KAKAO_CODE_NOT_VALID);
        }
    }

    private String getRequestResult(HttpURLConnection conn){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
