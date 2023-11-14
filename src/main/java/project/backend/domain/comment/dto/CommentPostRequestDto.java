package project.backend.domain.comment.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentPostRequestDto {
    public String content;
}