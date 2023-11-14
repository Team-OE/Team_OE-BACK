package project.backend.domain.feed.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedPostRequestDto {
    @NotBlank(message = "제목은 필수로 입력해야 합니다.")
    public String title;
    @NotBlank(message = "내용은 필수로 입력해야 합니다.")
    public String content;
    @NotBlank(message = "해시태그는 필수로 입력해야 합니다.")
    public String hashtag;
    public String imageUrl1;
    public String imageUrl2;
    public String imageUrl3;
    @NotBlank(message = "위도는 필수로 입력해야 합니다.")
    public String latitude;
    @NotBlank(message = "경도는 필수로 입력해야 합니다.")
    public String longitude;
    @NotBlank(message = "카테고리는 필수로 입력해야 합니다.(시위, 축제, 자연재해)")
    public String category;
}