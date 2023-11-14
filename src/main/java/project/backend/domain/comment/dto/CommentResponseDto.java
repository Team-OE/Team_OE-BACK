package project.backend.domain.comment.dto;
import lombok.*;
import project.backend.domain.member.dto.MemberResponseDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    public String content;
    public LocalDateTime createdDate;
    public LocalDateTime updatedDate;
    public MemberResponseDto member;
}