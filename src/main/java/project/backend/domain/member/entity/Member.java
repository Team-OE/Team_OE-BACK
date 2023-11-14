package project.backend.domain.member.entity;

import project.backend.domain.comment.entity.Comment;
import project.backend.domain.common.entity.BaseEntity;
import project.backend.domain.feed.entity.Feed;
import project.backend.domain.feedlike.entity.FeedLike;
import project.backend.domain.member.dto.MemberPatchRequestDto;
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
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // todo : IDENTITY와 AUTO 차이점이 뭔지?
    @Column(name = "member_id")
    public Long id;

    @Column(name = "nickname")
    public String nickname;

    @Column(name = "profile_url")
    public String profileUrl;

    @Enumerated(value = EnumType.STRING)
    public SocialType socialType;

    public String socialId;

    public String refreshToken;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Feed> feeds = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<FeedLike> feedLikes = new ArrayList<>();


    @Builder
    public Member(SocialType socialType, String socialId, String nickname, String profileUrl, String refreshToken){
        this.socialType = socialType;
        this.socialId = socialId;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.refreshToken = refreshToken;
    }

    // Patch
    public Member patchMember(MemberPatchRequestDto memberPatchRequestDto){

        this.nickname = Optional.ofNullable(memberPatchRequestDto.getNickname()).orElse(this.nickname);
        this.profileUrl = Optional.ofNullable(memberPatchRequestDto.getProfileUrl()).orElse(this.profileUrl);
        this.refreshToken = Optional.ofNullable(memberPatchRequestDto.getRefreshToken()).orElse(this.refreshToken);

        return this;
    }

}
