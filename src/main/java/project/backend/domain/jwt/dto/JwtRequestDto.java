package project.backend.domain.jwt.dto;

import lombok.*;
import project.backend.domain.member.entity.SocialType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class JwtRequestDto {

    @NotNull(message = "socialId는 필수로 입력해야 합니다.")
    public String socialId;
    @NotNull(message = "socialType은 필수로 입력해야 합니다. (KAKAO, APPLE)")
    public SocialType socialType;
}
