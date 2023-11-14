package project.backend.domain.feedlike.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import project.backend.domain.feedlike.dto.FeedLikePatchRequestDto;
import project.backend.domain.feedlike.dto.FeedLikePostRequestDto;
import project.backend.domain.feedlike.dto.FeedLikeResponseDto;
import project.backend.domain.feedlike.dto.FeedLikeResponseDto.FeedLikeResponseDtoBuilder;
import project.backend.domain.feedlike.entity.FeedLike;
import project.backend.domain.feedlike.entity.FeedLike.FeedLikeBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-14T22:22:26+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16.1 (Oracle Corporation)"
)
@Component
public class FeedLikeMapperImpl implements FeedLikeMapper {

    @Override
    public FeedLike feedLikePostRequestDtoToFeedLike(FeedLikePostRequestDto feedLikePostRequestDto) {
        if ( feedLikePostRequestDto == null ) {
            return null;
        }

        FeedLikeBuilder feedLike = FeedLike.builder();

        feedLike.name( feedLikePostRequestDto.getName() );

        return feedLike.build();
    }

    @Override
    public FeedLike feedLikePatchRequestDtoToFeedLike(FeedLikePatchRequestDto feedLikePatchRequestDto) {
        if ( feedLikePatchRequestDto == null ) {
            return null;
        }

        FeedLikeBuilder feedLike = FeedLike.builder();

        feedLike.name( feedLikePatchRequestDto.getName() );

        return feedLike.build();
    }

    @Override
    public FeedLikeResponseDto feedLikeToFeedLikeResponseDto(FeedLike feedLike) {
        if ( feedLike == null ) {
            return null;
        }

        FeedLikeResponseDtoBuilder feedLikeResponseDto = FeedLikeResponseDto.builder();

        feedLikeResponseDto.name( feedLike.getName() );

        return feedLikeResponseDto.build();
    }

    @Override
    public List<FeedLikeResponseDto> feedLikesToFeedLikeResponseDtos(List<FeedLike> feedLike) {
        if ( feedLike == null ) {
            return null;
        }

        List<FeedLikeResponseDto> list = new ArrayList<FeedLikeResponseDto>( feedLike.size() );
        for ( FeedLike feedLike1 : feedLike ) {
            list.add( feedLikeToFeedLikeResponseDto( feedLike1 ) );
        }

        return list;
    }
}
