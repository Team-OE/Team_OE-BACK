package project.backend.domain.feed.repository;

import project.backend.domain.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import project.backend.domain.member.entity.Member;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    List<Feed> findAllByMember(Member member);
}
