package project.backend.domain.hashtag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.backend.domain.hashtag.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
}
