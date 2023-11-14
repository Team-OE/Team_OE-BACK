package project.backend.domain.comment.mapper;

import project.backend.domain.comment.dto.CommentPatchRequestDto;
import project.backend.domain.comment.dto.CommentPostRequestDto;
import project.backend.domain.comment.dto.CommentResponseDto;
import project.backend.domain.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    Comment commentPostRequestDtoToComment(CommentPostRequestDto commentPostRequestDto);

    Comment commentPatchRequestDtoToComment(CommentPatchRequestDto commentPatchRequestDto);

    CommentResponseDto commentToCommentResponseDto(Comment comment);

    List<CommentResponseDto> commentsToCommentResponseDtos(List<Comment> comment);
}
