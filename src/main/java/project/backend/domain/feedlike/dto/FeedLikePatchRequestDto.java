package project.backend.domain.feedlike.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedLikePatchRequestDto {
    public String name;
}