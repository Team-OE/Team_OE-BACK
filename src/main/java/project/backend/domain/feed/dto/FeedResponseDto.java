package project.backend.domain.feed.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponseDto {
    public Long id;
    public String title;
    public String content;
    public String imageUrl1;
    public String imageUrl2;
    public String imageUrl3;
    public String latitude;
    public String longitude;
    public Integer likeCount;
    public Integer viewCount;
    public LocalDateTime createdDate;
    public LocalDateTime updatedDate;
}