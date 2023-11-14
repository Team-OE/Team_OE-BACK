package project.backend.domain.hashtag.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HashtagPatchRequestDto {
    public String name;
}