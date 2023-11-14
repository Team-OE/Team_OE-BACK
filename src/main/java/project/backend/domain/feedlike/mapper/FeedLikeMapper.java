package project.backend.domain.feedlike.mapper;

import project.backend.domain.feedlike.dto.FeedLikePatchRequestDto;
import project.backend.domain.feedlike.dto.FeedLikePostRequestDto;
import project.backend.domain.feedlike.dto.FeedLikeResponseDto;
import project.backend.domain.feedlike.entity.FeedLike;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedLikeMapper {
    FeedLike feedLikePostRequestDtoToFeedLike(FeedLikePostRequestDto feedLikePostRequestDto);

    FeedLike feedLikePatchRequestDtoToFeedLike(FeedLikePatchRequestDto feedLikePatchRequestDto);

    FeedLikeResponseDto feedLikeToFeedLikeResponseDto(FeedLike feedLike);

    List<FeedLikeResponseDto> feedLikesToFeedLikeResponseDtos(List<FeedLike> feedLike);
}
