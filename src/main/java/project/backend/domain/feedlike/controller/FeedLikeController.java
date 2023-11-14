package project.backend.domain.feedlike.controller;

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

import java.util.List;

@Api(tags = "좋아요 API")
@RestController
@RequestMapping("/api/feedLikes")
@RequiredArgsConstructor
public class FeedLikeController {

    private final FeedLikeService feedLikeService;
    private final FeedLikeMapper feedLikeMapper;


    @PostMapping
    public ResponseEntity postFeedLike(@RequestBody(required = false) FeedLikePostRequestDto feedLikePostRequestDto) {
        if (ObjectUtils.isEmpty(feedLikePostRequestDto)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        FeedLike feedLike = feedLikeService.createFeedLike(feedLikePostRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(feedLikeMapper.feedLikeToFeedLikeResponseDto(feedLike));
    }

    @ApiOperation(value = "공지 목록")
    @GetMapping("/{feedLikeId}")
    public ResponseEntity getFeedLike(@PathVariable(required = false) Long feedLikeId) {
        if (ObjectUtils.isEmpty(feedLikeId)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        FeedLikeResponseDto feedLikeResponseDto = feedLikeMapper.feedLikeToFeedLikeResponseDto(feedLikeService.getFeedLike(feedLikeId));
        return ResponseEntity.status(HttpStatus.OK).body(feedLikeResponseDto);
    }

    @GetMapping
    public ResponseEntity getFeedLikeList() {
        List<FeedLikeResponseDto> feedLikeResponseDtoList = feedLikeMapper.feedLikesToFeedLikeResponseDtos(feedLikeService.getFeedLikeList());
        return ResponseEntity.status(HttpStatus.OK).body(feedLikeResponseDtoList);
    }

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

    @DeleteMapping("/{feedLikeId}")
    public ResponseEntity deleteFeedLike(@PathVariable(required = false) Long feedLikeId) {
        if (ObjectUtils.isEmpty(feedLikeId)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        feedLikeService.deleteFeedLike(feedLikeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
