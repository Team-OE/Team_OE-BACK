package project.backend.domain.feedlike.service;

import project.backend.domain.feed.entity.Feed;
import project.backend.domain.feed.service.FeedService;
import project.backend.domain.feedlike.dto.FeedLikePatchRequestDto;
import project.backend.domain.feedlike.dto.FeedLikePostRequestDto;
import project.backend.domain.feedlike.dto.FeedLikeResponseDto;
import project.backend.domain.feedlike.entity.FeedLike;
import project.backend.domain.feedlike.mapper.FeedLikeMapper;
import project.backend.domain.feedlike.repository.FeedLikeRepository;
import project.backend.domain.jwt.service.JwtService;
import project.backend.domain.member.entity.Member;
import project.backend.global.error.exception.BusinessException;
import project.backend.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedLikeService {
    private final FeedLikeRepository feedLikeRepository;
    private final FeedLikeMapper feedLikeMapper;
    private final JwtService jwtService;
    private final FeedService feedService;

    public FeedLikeResponseDto createFeedLike(String accessToken, Long feedId) {

        Member member = jwtService.getMemberFromAccessToken(accessToken);
        Feed feed = feedService.verifiedFeed(feedId);
        List<FeedLike> feedLikeList = feedLikeRepository.findAllByFeedAndMember(feed, member);

        if (feedLikeList.size() > 0) {
            feedLikeRepository.deleteAll(feedLikeList);
            return FeedLikeResponseDto.builder().isLike(false).build();
        } else {
            FeedLike feedLike = feedLikeRepository.save(FeedLike.builder().build());
            feedLike.setMember(member);
            feedLike.setFeed(feed);
            return FeedLikeResponseDto.builder().isLike(true).build();
        }
    }

    public FeedLikeResponseDto getFeedLike(String accessToken, Long feedId) {
        Member member = jwtService.getMemberFromAccessToken(accessToken);
        Feed feed = feedService.verifiedFeed(feedId);
        List<FeedLike> feedLikeList = feedLikeRepository.findAllByFeedAndMember(feed, member);

        if (feedLikeList.size() > 0) {
            return FeedLikeResponseDto.builder().isLike(true).build();
        } else {
            return FeedLikeResponseDto.builder().isLike(false).build();
        }
    }

    @Transactional(readOnly = true)
    public List<Feed> getMyFeedLikeList(String accessToken) {
        List<Feed> feedList = new ArrayList<>();
        List<FeedLike> feedLikeList = jwtService.getMemberFromAccessToken(accessToken).getFeedLikes();
        for (FeedLike feedLike : feedLikeList) {
            feedList.add(feedLike.getFeed());
        }
        return feedList;
    }

    @Transactional(readOnly = true)
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
