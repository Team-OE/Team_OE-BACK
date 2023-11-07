package project.backend.domain.member.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.backend.domain.common.entity.BaseEntity;
import project.backend.domain.member.dto.MemberPatchRequestDto;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // todo : IDENTITY와 AUTO 차이점이 뭔지?
    @Column(name = "member_id")
    public Long id;

    @Enumerated(value = EnumType.STRING)
    public SocialType socialType;

    public String socialId;

    public String refreshToken;


    @Builder
    public Member(SocialType socialType, String socialId, String nickname, String profileUrl, String refreshToken){
        this.socialType = socialType;
        this.socialId = socialId;
        this.refreshToken = refreshToken;
    }

    // Patch
    public Member patchMember(MemberPatchRequestDto memberPatchRequestDto){
        this.refreshToken = Optional.ofNullable(memberPatchRequestDto.getRefreshToken()).orElse(this.refreshToken);
        return this;
    }
}
