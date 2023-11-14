package project.backend.domain.feed.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import project.backend.domain.category.dto.CategoryResponseDto;
import project.backend.domain.category.dto.CategoryResponseDto.CategoryResponseDtoBuilder;
import project.backend.domain.category.entity.Category;
import project.backend.domain.comment.dto.CommentResponseDto;
import project.backend.domain.comment.dto.CommentResponseDto.CommentResponseDtoBuilder;
import project.backend.domain.comment.entity.Comment;
import project.backend.domain.feed.dto.FeedPatchRequestDto;
import project.backend.domain.feed.dto.FeedPostRequestDto;
import project.backend.domain.feed.dto.FeedResponseDto;
import project.backend.domain.feed.dto.FeedResponseDto.FeedResponseDtoBuilder;
import project.backend.domain.feed.entity.Feed;
import project.backend.domain.feed.entity.Feed.FeedBuilder;
import project.backend.domain.hashtag.dto.HashtagResponseDto;
import project.backend.domain.hashtag.dto.HashtagResponseDto.HashtagResponseDtoBuilder;
import project.backend.domain.hashtag.entity.Hashtag;
import project.backend.domain.member.dto.MemberResponseDto;
import project.backend.domain.member.dto.MemberResponseDto.MemberResponseDtoBuilder;
import project.backend.domain.member.entity.Member;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-15T01:53:23+0900",
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
        feedResponseDto.hashtag( hashtagToHashtagResponseDto( feed.getHashtag() ) );
        feedResponseDto.member( memberToMemberResponseDto( feed.getMember() ) );
        feedResponseDto.category( categoryToCategoryResponseDto( feed.getCategory() ) );
        feedResponseDto.comments( commentListToCommentResponseDtoList( feed.getComments() ) );

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

    protected HashtagResponseDto hashtagToHashtagResponseDto(Hashtag hashtag) {
        if ( hashtag == null ) {
            return null;
        }

        HashtagResponseDtoBuilder hashtagResponseDto = HashtagResponseDto.builder();

        hashtagResponseDto.name( hashtag.getName() );

        return hashtagResponseDto.build();
    }

    protected MemberResponseDto memberToMemberResponseDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberResponseDtoBuilder memberResponseDto = MemberResponseDto.builder();

        memberResponseDto.id( member.getId() );
        memberResponseDto.nickname( member.getNickname() );
        memberResponseDto.createdDate( member.getCreatedDate() );
        memberResponseDto.updatedDate( member.getUpdatedDate() );

        return memberResponseDto.build();
    }

    protected CategoryResponseDto categoryToCategoryResponseDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponseDtoBuilder categoryResponseDto = CategoryResponseDto.builder();

        categoryResponseDto.name( category.getName() );

        return categoryResponseDto.build();
    }

    protected CommentResponseDto commentToCommentResponseDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentResponseDtoBuilder commentResponseDto = CommentResponseDto.builder();

        commentResponseDto.id( comment.getId() );
        commentResponseDto.content( comment.getContent() );
        commentResponseDto.createdDate( comment.getCreatedDate() );
        commentResponseDto.updatedDate( comment.getUpdatedDate() );
        commentResponseDto.member( memberToMemberResponseDto( comment.getMember() ) );

        return commentResponseDto.build();
    }

    protected List<CommentResponseDto> commentListToCommentResponseDtoList(List<Comment> list) {
        if ( list == null ) {
            return null;
        }

        List<CommentResponseDto> list1 = new ArrayList<CommentResponseDto>( list.size() );
        for ( Comment comment : list ) {
            list1.add( commentToCommentResponseDto( comment ) );
        }

        return list1;
    }
}
