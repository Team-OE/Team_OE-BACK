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
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "댓글 API - 완료 API(프론트 작업 가능)")
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @ApiOperation(value = "댓글 작성하기",
    notes = " - request : {\"content\" : \"댓글 내용 작성(필수)\", \"feedId\" : \"게시글 id(필수)\" }\n" +
            " - header('Authorization') : access Token(필수)")
    @PostMapping
    public ResponseEntity postComment(@RequestBody(required = false) CommentPostRequestDto request,
                                      @RequestHeader(value = "Authorization", required = false) String accessToken) {
        if (ObjectUtils.isEmpty(request) || ObjectUtils.isEmpty(accessToken)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        Comment comment = commentService.createComment(accessToken, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.commentToCommentResponseDto(comment));
    }

    @ApiIgnore
    @GetMapping("/{commentId}")
    public ResponseEntity getComment(@PathVariable(required = false) Long commentId) {
        if (ObjectUtils.isEmpty(commentId)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        CommentResponseDto commentResponseDto = commentMapper.commentToCommentResponseDto(commentService.getComment(commentId));
        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDto);
    }

    @ApiOperation(value = "내가 쓴 댓글 모아보기",
            notes = " - Header의 Authorization 필수")
    @GetMapping("/my")
    public ResponseEntity getMyCommentList(@RequestHeader(value = "Authorization", required = false) String accessToken) {
        if (ObjectUtils.isEmpty(accessToken)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        List<CommentResponseDto> commentResponseDtoList = commentMapper.commentsToCommentResponseDtos(commentService.getMyCommentList(accessToken));
        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDtoList);
    }

    @ApiIgnore
    @GetMapping
    public ResponseEntity getCommentList() {
        List<CommentResponseDto> commentResponseDtoList = commentMapper.commentsToCommentResponseDtos(commentService.getCommentList());
        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDtoList);
    }

    @ApiIgnore
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

    @ApiOperation(value = "댓글 삭제하기(내 댓글만)",
            notes = " - commentId : 댓글 id(필수)\n" +
                    " - header('Authorization') : access Token(필수)")
    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable(required = false) Long commentId,
                                        @RequestHeader(value = "Authorization", required = false) String accessToken) {
        if (ObjectUtils.isEmpty(commentId) || ObjectUtils.isEmpty(accessToken)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        commentService.deleteComment(accessToken, commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
