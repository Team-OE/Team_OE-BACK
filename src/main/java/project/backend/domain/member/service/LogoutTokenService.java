package project.backend.domain.member.service;

import project.backend.domain.member.entity.LogoutToken;
import project.backend.domain.member.repository.LogoutTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LogoutTokenService {

    private final LogoutTokenRepository logoutTokenRepository;

    public void memberLogout(String token) {
        logoutTokenRepository.save(LogoutToken.builder().token(token).build());
    }
}
