package project.backend.domain.feed.entity;

import project.backend.domain.category.entity.Category;
import project.backend.domain.comment.entity.Comment;
import project.backend.domain.common.entity.BaseEntity;
import project.backend.domain.feed.dto.FeedPatchRequestDto;
import project.backend.domain.feedlike.entity.FeedLike;
import project.backend.domain.hashtag.entity.Hashtag;
import project.backend.domain.member.entity.Member;
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
public class Feed extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    public Long id;

    @Column(name = "title")
    public String title;

    @Column(name = "content")
    public String content;

    @Column(name = "image_url1")
    public String imageUrl1;

    @Column(name = "image_url2")
    public String imageUrl2;

    @Column(name = "image_url3")
    public String imageUrl3;

    @Column(name = "latitude")
    public String latitude;

    @Column(name = "longitude")
    public String longitude;

    @Column(name = "like_count")
    public Integer likeCount;

    @Column(name = "view_count")
    public Integer viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    public Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    public Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    public Hashtag hashtag;

    @OneToMany(mappedBy = "feed", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<FeedLike> feedLikes = new ArrayList<>();


    @Builder
    public Feed(String title, String content, String imageUrl1, String imageUrl2, String imageUrl3, String latitude, String longitude){
        this.title = title;
        this.content = content;
        this.imageUrl1 = imageUrl1;
        this.imageUrl2 = imageUrl2;
        this.imageUrl3 = imageUrl3;
        this.latitude = latitude;
        this.longitude = longitude;
        this.likeCount = 0;
        this.viewCount = 0;
    }

    // Patch
    public Feed patchFeed(FeedPatchRequestDto feedPatchRequestDto){
        this.title = Optional.ofNullable(feedPatchRequestDto.getTitle()).orElse(this.title);
        this.content = Optional.ofNullable(feedPatchRequestDto.getContent()).orElse(this.content);
        this.imageUrl1 = Optional.ofNullable(feedPatchRequestDto.getImageUrl1()).orElse(this.imageUrl1);
        this.imageUrl2 = Optional.ofNullable(feedPatchRequestDto.getImageUrl2()).orElse(this.imageUrl2);
        this.imageUrl3 = Optional.ofNullable(feedPatchRequestDto.getImageUrl3()).orElse(this.imageUrl3);
        this.latitude = Optional.ofNullable(feedPatchRequestDto.getLatitude()).orElse(this.latitude);
        this.longitude = Optional.ofNullable(feedPatchRequestDto.getLongitude()).orElse(this.longitude);
        return this;
    }

    // == 연관관계 매핑 == //
    public void setMember(Member member) {
        if (this.member != null) {
            if (this.member.getFeeds().contains(this)) {
                this.member.getFeeds().remove(this);
            }
        }
        this.member = Optional.ofNullable(member).orElse(this.member);
        this.member.getFeeds().add(this);
    }

    public void setCategory(Category category) {
        if (this.category != null) {
            if (this.category.getFeeds().contains(this)) {
                this.category.getFeeds().remove(this);
            }
        }
        this.category = Optional.ofNullable(category).orElse(this.category);
        this.category.getFeeds().add(this);
    }

    public void setHashtag(Hashtag hashtag) {
        if (this.hashtag != null) {
            if (this.hashtag.getFeeds().contains(this)) {
                this.hashtag.getFeeds().remove(this);
            }
        }
        this.hashtag = Optional.ofNullable(hashtag).orElse(this.hashtag);
        this.hashtag.getFeeds().add(this);
    }
}
