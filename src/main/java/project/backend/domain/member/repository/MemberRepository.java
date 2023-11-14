package project.backend.domain.member.repository;

import project.backend.domain.member.entity.Member;
import project.backend.domain.member.entity.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findFirstBySocialIdAndSocialType(String socialId, SocialType socialType);

    List<Member> findAllByNickname(String nickname);
}
