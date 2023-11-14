package project.backend.domain.hashtag.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HashtagPostRequestDto {
    public String name;
}