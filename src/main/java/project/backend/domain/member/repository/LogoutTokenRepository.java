package project.backend.domain.member.repository;

import project.backend.domain.member.entity.LogoutToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogoutTokenRepository extends JpaRepository<LogoutToken, Long> {
    List<LogoutToken> findAllByToken(String token);
}
