package project.backend.domain.feed.dto;

import lombok.*;
import project.backend.domain.category.dto.CategoryResponseDto;
import project.backend.domain.comment.dto.CommentResponseDto;
import project.backend.domain.hashtag.dto.HashtagResponseDto;
import project.backend.domain.member.dto.MemberResponseDto;

import java.time.LocalDateTime;
import java.util.List;

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
    public HashtagResponseDto hashtag;
    public MemberResponseDto member;
    public CategoryResponseDto category;
    public List<CommentResponseDto> comments;
}