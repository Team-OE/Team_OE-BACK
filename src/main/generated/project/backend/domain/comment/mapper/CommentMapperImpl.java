package project.backend.domain.comment.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import project.backend.domain.comment.dto.CommentPatchRequestDto;
import project.backend.domain.comment.dto.CommentPostRequestDto;
import project.backend.domain.comment.dto.CommentResponseDto;
import project.backend.domain.comment.dto.CommentResponseDto.CommentResponseDtoBuilder;
import project.backend.domain.comment.entity.Comment;
import project.backend.domain.comment.entity.Comment.CommentBuilder;
import project.backend.domain.member.dto.MemberResponseDto;
import project.backend.domain.member.dto.MemberResponseDto.MemberResponseDtoBuilder;
import project.backend.domain.member.entity.Member;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-15T01:27:29+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16.1 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment commentPostRequestDtoToComment(CommentPostRequestDto commentPostRequestDto) {
        if ( commentPostRequestDto == null ) {
            return null;
        }

        CommentBuilder comment = Comment.builder();

        comment.content( commentPostRequestDto.getContent() );

        return comment.build();
    }

    @Override
    public Comment commentPatchRequestDtoToComment(CommentPatchRequestDto commentPatchRequestDto) {
        if ( commentPatchRequestDto == null ) {
            return null;
        }

        CommentBuilder comment = Comment.builder();

        comment.content( commentPatchRequestDto.getContent() );

        return comment.build();
    }

    @Override
    public CommentResponseDto commentToCommentResponseDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentResponseDtoBuilder commentResponseDto = CommentResponseDto.builder();

        commentResponseDto.content( comment.getContent() );
        commentResponseDto.createdDate( comment.getCreatedDate() );
        commentResponseDto.updatedDate( comment.getUpdatedDate() );
        commentResponseDto.member( memberToMemberResponseDto( comment.getMember() ) );

        return commentResponseDto.build();
    }

    @Override
    public List<CommentResponseDto> commentsToCommentResponseDtos(List<Comment> comment) {
        if ( comment == null ) {
            return null;
        }

        List<CommentResponseDto> list = new ArrayList<CommentResponseDto>( comment.size() );
        for ( Comment comment1 : comment ) {
            list.add( commentToCommentResponseDto( comment1 ) );
        }

        return list;
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
}
