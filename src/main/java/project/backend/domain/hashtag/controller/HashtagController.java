package project.backend.domain.hashtag.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import project.backend.domain.hashtag.dto.HashtagPatchRequestDto;
import project.backend.domain.hashtag.dto.HashtagPostRequestDto;
import project.backend.domain.hashtag.dto.HashtagResponseDto;
import project.backend.domain.hashtag.entity.Hashtag;
import project.backend.domain.hashtag.mapper.HashtagMapper;
import project.backend.domain.hashtag.service.HashtagService;
import project.backend.global.error.exception.BusinessException;
import project.backend.global.error.exception.ErrorCode;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "해시태그 API  - 완료 API(프론트 작업 가능)")
@RestController
@RequestMapping("/api/hashtags")
@RequiredArgsConstructor
public class HashtagController {

    private final HashtagService hashtagService;
    private final HashtagMapper hashtagMapper;


    @ApiIgnore
    @PostMapping
    public ResponseEntity postHashtag(@RequestBody(required = false) HashtagPostRequestDto hashtagPostRequestDto) {
        if (ObjectUtils.isEmpty(hashtagPostRequestDto)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        Hashtag hashtag = hashtagService.createHashtag(hashtagPostRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(hashtagMapper.hashtagToHashtagResponseDto(hashtag));
    }

    @ApiIgnore
    @GetMapping("/{hashtagId}")
    public ResponseEntity getHashtag(@PathVariable(required = false) Long hashtagId) {
        if (ObjectUtils.isEmpty(hashtagId)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        HashtagResponseDto hashtagResponseDto = hashtagMapper.hashtagToHashtagResponseDto(hashtagService.getHashtag(hashtagId));
        return ResponseEntity.status(HttpStatus.OK).body(hashtagResponseDto);
    }

    @ApiOperation(value = "해시태그 목록 조회")
    @GetMapping
    public ResponseEntity getHashtagList() {
        List<HashtagResponseDto> hashtagResponseDtoList = hashtagMapper.hashtagsToHashtagResponseDtos(hashtagService.getHashtagList());
        return ResponseEntity.status(HttpStatus.OK).body(hashtagResponseDtoList);
    }

    @ApiIgnore
    @PatchMapping("/{hashtagId}")
    public ResponseEntity putHashtag(
            @PathVariable(required = false) Long hashtagId,
            @RequestBody(required = false) HashtagPatchRequestDto hashtagPatchRequestDto) {
        if (ObjectUtils.isEmpty(hashtagId) || ObjectUtils.isEmpty(hashtagPatchRequestDto)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        HashtagResponseDto hashtagResponseDto = hashtagMapper.hashtagToHashtagResponseDto(hashtagService.patchHashtag(hashtagId, hashtagPatchRequestDto));
        return ResponseEntity.status(HttpStatus.OK).body(hashtagResponseDto);
    }

    @ApiIgnore
    @DeleteMapping("/{hashtagId}")
    public ResponseEntity deleteHashtag(@PathVariable(required = false) Long hashtagId) {
        if (ObjectUtils.isEmpty(hashtagId)){
            throw new BusinessException(ErrorCode.MISSING_REQUEST);
        }
        hashtagService.deleteHashtag(hashtagId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
