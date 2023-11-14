package project.backend.domain.member.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NicknameResponseDto {
    private String nickname;
    private String message;
}
