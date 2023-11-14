package project.backend.domain.feedlike.repository;

import project.backend.domain.feed.entity.Feed;
import project.backend.domain.feedlike.entity.FeedLike;
import org.springframework.data.jpa.repository.JpaRepository;
import project.backend.domain.member.entity.Member;

import java.util.List;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {
    List<FeedLike> findAllByFeedAndMember(Feed feed, Member member);
}
