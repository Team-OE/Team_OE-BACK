package project.backend.domain.feedlike.service;

import project.backend.domain.feedlike.dto.FeedLikePatchRequestDto;
import project.backend.domain.feedlike.dto.FeedLikePostRequestDto;
import project.backend.domain.feedlike.entity.FeedLike;
import project.backend.domain.feedlike.mapper.FeedLikeMapper;
import project.backend.domain.feedlike.repository.FeedLikeRepository;
import project.backend.global.error.exception.BusinessException;
import project.backend.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedLikeService {
    private final FeedLikeRepository feedLikeRepository;
    private final FeedLikeMapper feedLikeMapper;

    public FeedLike createFeedLike(FeedLikePostRequestDto feedLikePostRequestDto){
        FeedLike feedLike = FeedLike.builder()
                .name(feedLikePostRequestDto.getName()).build();
        feedLikeRepository.save(feedLike);
        return feedLike;
    }

    public FeedLike getFeedLike(Long id) {
        return verifiedFeedLike(id);
    }

    public List<FeedLike> getFeedLikeList() {
        return feedLikeRepository.findAll();
    }

    public FeedLike patchFeedLike(Long id, FeedLikePatchRequestDto feedLikePatchRequestDto) {
        FeedLike feedLike = verifiedFeedLike(id).patchFeedLike(feedLikePatchRequestDto);
        feedLikeRepository.save(feedLike);
        return feedLike;
    }

    public void deleteFeedLike(Long id) {
        feedLikeRepository.delete(verifiedFeedLike(id));
    }

    private FeedLike verifiedFeedLike(Long id) {
        return feedLikeRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
    }

}
