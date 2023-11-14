package project.backend.domain.feedlike.entity;

import project.backend.domain.common.entity.BaseEntity;
import project.backend.domain.feed.entity.Feed;
import project.backend.domain.feedlike.dto.FeedLikePatchRequestDto;
import project.backend.domain.member.entity.Member;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedLike_id")
    public Long id;

    @Column(name = "name")
    public String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    public Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    public Feed feed;

    @Builder
    public FeedLike(String name){
        this.name = name;
    }

    // Patch
    public FeedLike patchFeedLike(FeedLikePatchRequestDto feedLikePatchRequestDto){
        this.name = Optional.ofNullable(feedLikePatchRequestDto.getName()).orElse(this.name);
        return this;
    }

    // == 연관관계 매핑 == //
    public void setMember(Member member) {
        if (this.member != null) {
            if (this.member.getFeedLikes().contains(this)) {
                this.member.getFeedLikes().remove(this);
            }
        }
        this.member = Optional.ofNullable(member).orElse(this.member);
        this.member.getFeedLikes().add(this);
    }

    public void setFeed(Feed feed) {
        if (this.feed != null) {
            if (this.feed.getFeedLikes().contains(this)) {
                this.feed.getFeedLikes().remove(this);
            }
        }
        this.feed = Optional.ofNullable(feed).orElse(this.feed);
        this.feed.getFeedLikes().add(this);
    }
}
