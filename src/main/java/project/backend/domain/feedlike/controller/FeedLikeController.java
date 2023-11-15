package project.backend.domain.feedlike.controller;

import project.backend.domain.feed.dto.FeedResponseDto;
import project.backend.domain.feed.mapper.FeedMapper;
import project.backend.domain.feedlike.dto.FeedLikePatchRequestDto;
import project.backend.domain.feedlike.dto.FeedLikePostRequestDto;
import project.backend.domain.feedlike.dto.FeedLikeResponseDto;
import project.backend.domain.feedlike.entity.FeedLike;
import project.backend.domain.feedlike.mapper.FeedLikeMapper;
import project.backend.domain.feedlike.service.FeedLikeService;
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

import java.nio.file.Path;
import java.util.List;

@Api(tags = "좋아요 API - 완료 API(프론트 작업 가능)")
@RestController
@RequestMapping("/api/feedLikes")
@RequiredArgsConstructor
public class FeedLikeController {

    private final FeedLikeService feedLikeService;
    private final FeedLikeMapper feedLikeMapper;
    private final FeedMapper feedMapper;


    @ApiOperation(value = "좋아요 상태 변경",
            notes = " - Header('Authorization') : Access Token(필수)\n" +
                    " - feedId : 게시글 id(필수)")
    @PostMapping("/{feedId}")
    public ResponseEntity postFeedLike(
            @RequestHeader(value = "Authorization",required = false) String accessToken,
            @PathVariable(value ="feedId", required = false) Long feedId) {
        if (ObjectUtils.isEmpty(accessToken) || ObjectUtils.isEmpty(feedId)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        FeedLikeResponseDto feedLikeResponseDto = feedLikeService.createFeedLike(accessToken, feedId);
        return ResponseEntity.status(HttpStatus.CREATED).body(feedLikeResponseDto);
    }

    @ApiOperation(value = "좋아요 여부 확인",
            notes = " - Header('Authorization') : Access Token(필수)\n" +
                    " - feedId : 게시글 id(필수)")
    @GetMapping("/{feedId}")
    public ResponseEntity getFeedLike(@RequestHeader(value = "Authorization",required = false) String accessToken,
                                      @PathVariable(value ="feedId", required = false) Long feedId) {
        if (ObjectUtils.isEmpty(accessToken) || ObjectUtils.isEmpty(feedId)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        FeedLikeResponseDto feedLikeResponseDto = feedLikeService.getFeedLike(accessToken, feedId);
        return ResponseEntity.status(HttpStatus.CREATED).body(feedLikeResponseDto);
    }
    @ApiOperation(value = "내가 누른 좋아요 모아보기",
            notes = " - Header의 Authorization 필수")
    @GetMapping("/my")
    public ResponseEntity getMyFeedLikeList(
            @RequestHeader(value = "Authorization", required = false) String accessToken
    ) {
        if (ObjectUtils.isEmpty(accessToken)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        List<FeedResponseDto> feedResponseDtoList = feedMapper.feedsToFeedResponseDtos(feedLikeService.getMyFeedLikeList(accessToken));
        return ResponseEntity.status(HttpStatus.OK).body(feedResponseDtoList);
    }

    @ApiIgnore
    @GetMapping
    public ResponseEntity getFeedLikeList() {
        List<FeedLikeResponseDto> feedLikeResponseDtoList = feedLikeMapper.feedLikesToFeedLikeResponseDtos(feedLikeService.getFeedLikeList());
        return ResponseEntity.status(HttpStatus.OK).body(feedLikeResponseDtoList);
    }

    @ApiIgnore
    @PatchMapping("/{feedLikeId}")
    public ResponseEntity putFeedLike(
            @PathVariable(required = false) Long feedLikeId,
            @RequestBody(required = false) FeedLikePatchRequestDto feedLikePatchRequestDto) {
        if (ObjectUtils.isEmpty(feedLikeId) || ObjectUtils.isEmpty(feedLikePatchRequestDto)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        FeedLikeResponseDto feedLikeResponseDto = feedLikeMapper.feedLikeToFeedLikeResponseDto(feedLikeService.patchFeedLike(feedLikeId, feedLikePatchRequestDto));
        return ResponseEntity.status(HttpStatus.OK).body(feedLikeResponseDto);
    }

    @ApiIgnore
    @DeleteMapping("/{feedLikeId}")
    public ResponseEntity deleteFeedLike(@PathVariable(required = false) Long feedLikeId) {
        if (ObjectUtils.isEmpty(feedLikeId)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        feedLikeService.deleteFeedLike(feedLikeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
