package project.backend.domain.member.dto;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberPatchRequestDto {
    public String nickname;
    public String profileUrl;
    public String refreshToken;
}
