package project.backend.domain.feedlike.repository;

import project.backend.domain.feedlike.entity.FeedLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {
}
