package project.backend.domain.feed.service;

import project.backend.domain.feed.dto.FeedPatchRequestDto;
import project.backend.domain.feed.dto.FeedPostRequestDto;
import project.backend.domain.feed.entity.Feed;
import project.backend.domain.feed.mapper.FeedMapper;
import project.backend.domain.feed.repository.FeedRepository;
import project.backend.global.error.exception.BusinessException;
import project .backend.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedService {
    private final FeedRepository feedRepository;
    private final FeedMapper feedMapper;

    public Feed createFeed(FeedPostRequestDto feedPostRequestDto){
        Feed feed = Feed.builder()
                .title(feedPostRequestDto.getTitle())
                .content(feedPostRequestDto.getContent())
                .imageUrl1(feedPostRequestDto.getImageUrl1())
                .imageUrl2(feedPostRequestDto.getImageUrl2())
                .imageUrl3(feedPostRequestDto.getImageUrl3())
                .hashtag(feedPostRequestDto.getHashtag())
                .latitude(feedPostRequestDto.getLatitude())
                .longitude(feedPostRequestDto.getLongitude())
                .build();
        feedRepository.save(feed);
        return feed;
    }

    public Feed getFeed(Long id) {
        return verifiedFeed(id);
    }

    public List<Feed> getFeedList() {
        return feedRepository.findAll();
    }

    public Feed patchFeed(Long id, FeedPatchRequestDto feedPatchRequestDto) {
        Feed feed = verifiedFeed(id).patchFeed(feedPatchRequestDto);
        feedRepository.save(feed);
        return feed;
    }

    public void deleteFeed(Long id) {
        feedRepository.delete(verifiedFeed(id));
    }

    private Feed verifiedFeed(Long id) {
        return feedRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
    }

}
