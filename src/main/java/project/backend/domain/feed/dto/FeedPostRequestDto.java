package project.backend.domain.feed.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedPostRequestDto {
    public String title;
    public String content;
    public String imageUrl1;
    public String imageUrl2;
    public String imageUrl3;
    public String latitude;
    public String longitude;
}