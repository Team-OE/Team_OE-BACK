package project.backend.domain.comment.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentPostRequestDto {
    @NotBlank(message = "댓글 내용을 작성해야 합니다.")
    public String content;
    @NotBlank(message = "게시글 Id를 작성해야 합니다.")
    public Long feedId;
}