package project.backend.domain.comment.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentPatchRequestDto {
    public String content;
}