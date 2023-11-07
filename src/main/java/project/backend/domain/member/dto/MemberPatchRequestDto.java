package project.backend.domain.member.dto;

import lombok.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberPatchRequestDto {
    public String refreshToken;
}
