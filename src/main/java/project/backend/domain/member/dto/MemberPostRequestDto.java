package project.backend.domain.member.dto;

import project.backend.domain.member.entity.SocialType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberPostRequestDto {
    public String socialId;
    public SocialType socialType;
}
