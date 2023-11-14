package project.backend.domain.category.entity;

import project.backend.domain.category.dto.CategoryPatchRequestDto;
import project.backend.domain.common.entity.BaseEntity;
import project.backend.domain.feed.entity.Feed;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    public Long id;

    @Column(name = "name")
    public String name;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Feed> feeds = new ArrayList<>();

    @Builder
    public Category(String name){
        this.name = name;
    }

    // Patch
    public Category patchCategory(CategoryPatchRequestDto categoryPatchRequestDto){
        this.name = Optional.ofNullable(categoryPatchRequestDto.getName()).orElse(this.name);
        return this;
    }
}
