package project.backend.domain.feedlike.dto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedLikeResponseDto {
    public Boolean isLike;
}