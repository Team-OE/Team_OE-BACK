package project.backend.domain.comment.controller;

import project.backend.domain.comment.dto.CommentPatchRequestDto;
import project.backend.domain.comment.dto.CommentPostRequestDto;
import project.backend.domain.comment.dto.CommentResponseDto;
import project.backend.domain.comment.entity.Comment;
import project.backend.domain.comment.mapper.CommentMapper;
import project.backend.domain.comment.service.CommentService;
import project.backend.global.error.exception.BusinessException;
import project.backend.global.error.exception.ErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "댓글 API")
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;


    @PostMapping
    public ResponseEntity postComment(@RequestBody(required = false) CommentPostRequestDto commentPostRequestDto) {
        if (ObjectUtils.isEmpty(commentPostRequestDto)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        Comment comment = commentService.createComment(commentPostRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.commentToCommentResponseDto(comment));
    }

    @ApiOperation(value = "공지 목록")
    @GetMapping("/{commentId}")
    public ResponseEntity getComment(@PathVariable(required = false) Long commentId) {
        if (ObjectUtils.isEmpty(commentId)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        CommentResponseDto commentResponseDto = commentMapper.commentToCommentResponseDto(commentService.getComment(commentId));
        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDto);
    }

    @GetMapping
    public ResponseEntity getCommentList() {
        List<CommentResponseDto> commentResponseDtoList = commentMapper.commentsToCommentResponseDtos(commentService.getCommentList());
        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDtoList);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity putComment(
            @PathVariable(required = false) Long commentId,
            @RequestBody(required = false) CommentPatchRequestDto commentPatchRequestDto) {
        if (ObjectUtils.isEmpty(commentId) || ObjectUtils.isEmpty(commentPatchRequestDto)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        CommentResponseDto commentResponseDto = commentMapper.commentToCommentResponseDto(commentService.patchComment(commentId, commentPatchRequestDto));
        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable(required = false) Long commentId) {
        if (ObjectUtils.isEmpty(commentId)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
