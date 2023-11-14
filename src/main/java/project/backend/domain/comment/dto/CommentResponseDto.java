package project.backend.domain.comment.dto;
import lombok.*;
import project.backend.domain.feed.dto.FeedResponseDto;
import project.backend.domain.member.dto.MemberResponseDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    public Long id;
    public String content;
    public LocalDateTime createdDate;
    public LocalDateTime updatedDate;
    public MemberResponseDto member;
}