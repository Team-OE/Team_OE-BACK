package project.backend.domain.feed.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import project.backend.domain.feed.dto.FeedPatchRequestDto;
import project.backend.domain.feed.dto.FeedPostRequestDto;
import project.backend.domain.feed.dto.FeedResponseDto;
import project.backend.domain.feed.dto.FeedResponseDto.FeedResponseDtoBuilder;
import project.backend.domain.feed.entity.Feed;
import project.backend.domain.feed.entity.Feed.FeedBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-14T22:22:26+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16.1 (Oracle Corporation)"
)
@Component
public class FeedMapperImpl implements FeedMapper {

    @Override
    public Feed feedPostRequestDtoToFeed(FeedPostRequestDto feedPostRequestDto) {
        if ( feedPostRequestDto == null ) {
            return null;
        }

        FeedBuilder feed = Feed.builder();

        feed.title( feedPostRequestDto.getTitle() );
        feed.content( feedPostRequestDto.getContent() );
        feed.imageUrl1( feedPostRequestDto.getImageUrl1() );
        feed.imageUrl2( feedPostRequestDto.getImageUrl2() );
        feed.imageUrl3( feedPostRequestDto.getImageUrl3() );
        feed.latitude( feedPostRequestDto.getLatitude() );
        feed.longitude( feedPostRequestDto.getLongitude() );

        return feed.build();
    }

    @Override
    public Feed feedPatchRequestDtoToFeed(FeedPatchRequestDto feedPatchRequestDto) {
        if ( feedPatchRequestDto == null ) {
            return null;
        }

        FeedBuilder feed = Feed.builder();

        feed.title( feedPatchRequestDto.getTitle() );
        feed.content( feedPatchRequestDto.getContent() );
        feed.imageUrl1( feedPatchRequestDto.getImageUrl1() );
        feed.imageUrl2( feedPatchRequestDto.getImageUrl2() );
        feed.imageUrl3( feedPatchRequestDto.getImageUrl3() );
        feed.latitude( feedPatchRequestDto.getLatitude() );
        feed.longitude( feedPatchRequestDto.getLongitude() );

        return feed.build();
    }

    @Override
    public FeedResponseDto feedToFeedResponseDto(Feed feed) {
        if ( feed == null ) {
            return null;
        }

        FeedResponseDtoBuilder feedResponseDto = FeedResponseDto.builder();

        feedResponseDto.id( feed.getId() );
        feedResponseDto.title( feed.getTitle() );
        feedResponseDto.content( feed.getContent() );
        feedResponseDto.imageUrl1( feed.getImageUrl1() );
        feedResponseDto.imageUrl2( feed.getImageUrl2() );
        feedResponseDto.imageUrl3( feed.getImageUrl3() );
        feedResponseDto.latitude( feed.getLatitude() );
        feedResponseDto.longitude( feed.getLongitude() );
        feedResponseDto.likeCount( feed.getLikeCount() );
        feedResponseDto.viewCount( feed.getViewCount() );
        feedResponseDto.createdDate( feed.getCreatedDate() );
        feedResponseDto.updatedDate( feed.getUpdatedDate() );

        return feedResponseDto.build();
    }

    @Override
    public List<FeedResponseDto> feedsToFeedResponseDtos(List<Feed> feed) {
        if ( feed == null ) {
            return null;
        }

        List<FeedResponseDto> list = new ArrayList<FeedResponseDto>( feed.size() );
        for ( Feed feed1 : feed ) {
            list.add( feedToFeedResponseDto( feed1 ) );
        }

        return list;
    }
}
