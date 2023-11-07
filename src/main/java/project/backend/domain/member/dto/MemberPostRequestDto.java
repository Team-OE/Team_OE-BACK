package project.backend.domain.member.dto;

import lombok.*;
import project.backend.domain.member.entity.SocialType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberPostRequestDto {
    public String socialId;
    public SocialType socialType;
}
