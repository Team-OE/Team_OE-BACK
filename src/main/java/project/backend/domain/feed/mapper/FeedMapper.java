package project.backend.domain.feed.mapper;

import project.backend.domain.feed.dto.FeedPatchRequestDto;
import project.backend.domain.feed.dto.FeedPostRequestDto;
import project.backend.domain.feed.dto.FeedResponseDto;
import project.backend.domain.feed.entity.Feed;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedMapper {
    Feed feedPostRequestDtoToFeed(FeedPostRequestDto feedPostRequestDto);

    Feed feedPatchRequestDtoToFeed(FeedPatchRequestDto feedPatchRequestDto);

    FeedResponseDto feedToFeedResponseDto(Feed feed);

    List<FeedResponseDto> feedsToFeedResponseDtos(List<Feed> feed);
}
