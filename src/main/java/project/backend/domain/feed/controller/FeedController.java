package project.backend.domain.feed.controller;

import org.springframework.web.multipart.MultipartFile;
import project.backend.domain.feed.dto.FeedPatchRequestDto;
import project.backend.domain.feed.dto.FeedPostRequestDto;
import project.backend.domain.feed.dto.FeedResponseDto;
import project.backend.domain.feed.entity.Feed;
import project.backend.domain.feed.mapper.FeedMapper;
import project.backend.domain.feed.service.FeedService;
import project.backend.domain.member.dto.MemberPatchRequestDto;
import project.backend.domain.member.entity.Member;
import project.backend.global.error.exception.BusinessException;
import project.backend.global.error.exception.ErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import project.backend.global.s3.service.ImageService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "게시글 API")
@RestController
@RequestMapping("/api/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    private final FeedMapper feedMapper;
    private final ImageService imageService;

    @ApiOperation(value = "게시글 작성",
            notes = " - request(application/json) : {\"title\" : \"제목 입력(필수)\", \"content\" : \"내용 입력(필수)\", \"hashtag\" : \"해시태그 입력(필수)\", \"latitude\" : \"위도(필수)\", \"longitude\" : \"경도(필수)\", \"category\" : \"카테고리 입력(필수)\"}\n" +
                    " - Header('Authorization') : accessToken 입력(필수)\n" +
                    " - image1, image2, image3: 이미지 파일(선택)")
    @PostMapping
    public ResponseEntity postFeed(
            @RequestHeader(value = "Authorization", required = false) String accessToken,
            @RequestPart(required = false) FeedPostRequestDto request,
            @RequestPart(required = false) MultipartFile image1,
            @RequestPart(required = false) MultipartFile image2,
            @RequestPart(required = false) MultipartFile image3 ) {
        if (ObjectUtils.isEmpty(request) || ObjectUtils.isEmpty(accessToken)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        if (!ObjectUtils.isEmpty(image1)){
            request.setImageUrl1(imageService.updateImage(image1, "Feed", "imageUrl1"));
        }
        if (!ObjectUtils.isEmpty(image2)){
            request.setImageUrl2(imageService.updateImage(image2, "Feed", "imageUrl2"));
        }
        if (!ObjectUtils.isEmpty(image3)){
            request.setImageUrl3(imageService.updateImage(image3, "Feed", "imageUrl3"));
        }

        Feed feed = feedService.createFeed(request, accessToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(feedMapper.feedToFeedResponseDto(feed));
    }

    @ApiOperation(value = "게시글 확인하기",
            notes = "- feedId : 게시글 id (필수)")
    @GetMapping("/{feedId}")
    public ResponseEntity getFeed(@PathVariable(required = false) Long feedId) {
        if (ObjectUtils.isEmpty(feedId)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        FeedResponseDto feedResponseDto = feedMapper.feedToFeedResponseDto(feedService.getFeed(feedId));
        return ResponseEntity.status(HttpStatus.OK).body(feedResponseDto);
    }

    @ApiIgnore
    @GetMapping
    public ResponseEntity getFeedList() {
        List<FeedResponseDto> feedResponseDtoList = feedMapper.feedsToFeedResponseDtos(feedService.getFeedList());
        return ResponseEntity.status(HttpStatus.OK).body(feedResponseDtoList);
    }

    @ApiOperation(value = "내 게시글 수정",
            notes = " - request(application/json) : {\"title\" : \"제목 입력(선택, 공백은 불가)\", \"content\" : \"내용 입력(선택, 공백은 불가)\", \"hashtag\" : \"해시태그 입력(선택, 공백은 불가)\", \"latitude\" : \"위도(선택, 공백은 불가)\", \"longitude\" : \"경도(선택, 공백은 불가)\"}\n" +
                    " - Header('Authorization') : accessToken(필수)\n" +
                    " - feedId : 게시글 id(필수)" +
                    " - image1, image2, image3는 선택")
    @PatchMapping("/{feedId}")
    public ResponseEntity patchFeed(
            @PathVariable(required = false) Long feedId,
            @RequestHeader(value = "Authorization", required = false) String accessToken,
            @RequestPart(required = false) FeedPatchRequestDto request,
            @RequestPart(required = false) MultipartFile image1,
            @RequestPart(required = false) MultipartFile image2,
            @RequestPart(required = false) MultipartFile image3 ) {
        if (ObjectUtils.isEmpty(accessToken) || ObjectUtils.isEmpty(feedId)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        if (!ObjectUtils.isEmpty(image1)){
            request.setImageUrl1(imageService.updateImage(image1, "Feed", "imageUrl1"));
        }
        if (!ObjectUtils.isEmpty(image2)){
            request.setImageUrl2(imageService.updateImage(image2, "Feed", "imageUrl2"));
        }
        if (!ObjectUtils.isEmpty(image3)){
            request.setImageUrl3(imageService.updateImage(image3, "Feed", "imageUrl3"));
        }

        Feed feed = feedService.patchFeed(feedId, request, accessToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(feedMapper.feedToFeedResponseDto(feed));
    }

    @DeleteMapping("/{feedId}")
    public ResponseEntity deleteFeed(@PathVariable(required = false) Long feedId,
                                     @RequestHeader(value = "Authorization", required = false) String accessToken) {
        if (ObjectUtils.isEmpty(feedId) || ObjectUtils.isEmpty(accessToken)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        feedService.deleteFeed(accessToken, feedId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
