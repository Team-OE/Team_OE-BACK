package project.backend.domain.hashtag.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.backend.domain.common.entity.BaseEntity;
import project.backend.domain.feed.entity.Feed;
import project.backend.domain.hashtag.dto.HashtagPatchRequestDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Hashtag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    public Long id;

    @Column(name = "name")
    public String name;

    @OneToMany(mappedBy = "hashtag", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Feed> feeds = new ArrayList<>();

    @Builder
    public Hashtag(String name){
        this.name = name;
    }

    // Patch
    public Hashtag patchHashtag(HashtagPatchRequestDto hashtagPatchRequestDto){
        this.name = Optional.ofNullable(hashtagPatchRequestDto.getName()).orElse(this.name);
        return this;
    }
}
