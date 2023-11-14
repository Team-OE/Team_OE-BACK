package project.backend.domain.hashtag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.backend.domain.hashtag.entity.Hashtag;

import java.util.List;
import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    List<Hashtag> findAllByName(String name);
}
