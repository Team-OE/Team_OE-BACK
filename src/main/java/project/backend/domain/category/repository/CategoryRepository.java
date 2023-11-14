package project.backend.domain.category.repository;

import project.backend.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findOneByName(String name);
}
