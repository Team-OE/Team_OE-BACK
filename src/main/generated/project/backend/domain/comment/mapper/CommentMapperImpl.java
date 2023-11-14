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

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-14T21:56:27+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.3.jar, environment: Java 11.0.16.1 (Oracle Corporation)"
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
}
