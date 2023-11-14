package project.backend.domain.member.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    public Long id;
    public LocalDateTime createdDate;
    public LocalDateTime updatedDate;
}
