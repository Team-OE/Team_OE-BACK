package project.backend.domain.member.entity;

import lombok.Getter;

@Getter
public enum SocialType {
    KAKAO("kakao"),
    GOOGLE("google"),
    APPLE("apple");

    private final String status;

    SocialType(String status) {
        this.status = status;
    }

}
