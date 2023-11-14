package project.backend.domain.comment.entity;

import project.backend.domain.comment.dto.CommentPatchRequestDto;
import project.backend.domain.common.entity.BaseEntity;
import project.backend.domain.feed.entity.Feed;
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
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    public Long id;

    @Column(name = "content")
    public String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    public Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    public Feed feed;

    @Builder
    public Comment(String content){
        this.content = content;
    }

    // Patch
    public Comment patchComment(CommentPatchRequestDto commentPatchRequestDto){
        this.content = Optional.ofNullable(commentPatchRequestDto.getContent()).orElse(this.content);
        return this;
    }


    // == 연관관계 매핑 == //
    public void setMember(Member member) {
        if (this.member != null) {
            if (this.member.getComments().contains(this)) {
                this.member.getComments().remove(this);
            }
        }
        this.member = Optional.ofNullable(member).orElse(this.member);
        this.member.getComments().add(this);
    }

    public void setFeed(Feed feed) {
        if (this.feed != null) {
            if (this.feed.getComments().contains(this)) {
                this.feed.getComments().remove(this);
            }
        }
        this.feed = Optional.ofNullable(feed).orElse(this.feed);
        this.feed.getComments().add(this);
    }
}
