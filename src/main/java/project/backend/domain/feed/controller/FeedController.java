package project.backend.domain.feed.controller;

import project.backend.domain.feed.dto.FeedPatchRequestDto;
import project.backend.domain.feed.dto.FeedPostRequestDto;
import project.backend.domain.feed.dto.FeedResponseDto;
import project.backend.domain.feed.entity.Feed;
import project.backend.domain.feed.mapper.FeedMapper;
import project.backend.domain.feed.service.FeedService;
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

@Api(tags = "게시글 API")
@RestController
@RequestMapping("/api/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    private final FeedMapper feedMapper;


    @PostMapping
    public ResponseEntity postFeed(@RequestBody(required = false) FeedPostRequestDto feedPostRequestDto) {
        if (ObjectUtils.isEmpty(feedPostRequestDto)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        Feed feed = feedService.createFeed(feedPostRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(feedMapper.feedToFeedResponseDto(feed));
    }

    @ApiOperation(value = "공지 목록")
    @GetMapping("/{feedId}")
    public ResponseEntity getFeed(@PathVariable(required = false) Long feedId) {
        if (ObjectUtils.isEmpty(feedId)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        FeedResponseDto feedResponseDto = feedMapper.feedToFeedResponseDto(feedService.getFeed(feedId));
        return ResponseEntity.status(HttpStatus.OK).body(feedResponseDto);
    }

    @GetMapping
    public ResponseEntity getFeedList() {
        List<FeedResponseDto> feedResponseDtoList = feedMapper.feedsToFeedResponseDtos(feedService.getFeedList());
        return ResponseEntity.status(HttpStatus.OK).body(feedResponseDtoList);
    }

    @PatchMapping("/{feedId}")
    public ResponseEntity putFeed(
            @PathVariable(required = false) Long feedId,
            @RequestBody(required = false) FeedPatchRequestDto feedPatchRequestDto) {
        if (ObjectUtils.isEmpty(feedId) || ObjectUtils.isEmpty(feedPatchRequestDto)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        FeedResponseDto feedResponseDto = feedMapper.feedToFeedResponseDto(feedService.patchFeed(feedId, feedPatchRequestDto));
        return ResponseEntity.status(HttpStatus.OK).body(feedResponseDto);
    }

    @DeleteMapping("/{feedId}")
    public ResponseEntity deleteFeed(@PathVariable(required = false) Long feedId) {
        if (ObjectUtils.isEmpty(feedId)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        feedService.deleteFeed(feedId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
