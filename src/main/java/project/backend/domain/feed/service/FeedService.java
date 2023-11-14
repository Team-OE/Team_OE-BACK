package project.backend.domain.feed.service;

import project.backend.domain.category.entity.Category;
import project.backend.domain.category.service.CategoryService;
import project.backend.domain.feed.dto.FeedPatchRequestDto;
import project.backend.domain.feed.dto.FeedPostRequestDto;
import project.backend.domain.feed.entity.Feed;
import project.backend.domain.feed.mapper.FeedMapper;
import project.backend.domain.feed.repository.FeedRepository;
import project.backend.domain.hashtag.dto.HashtagPostRequestDto;
import project.backend.domain.hashtag.entity.Hashtag;
import project.backend.domain.hashtag.repository.HashtagRepository;
import project.backend.domain.hashtag.service.HashtagService;
import project.backend.domain.jwt.service.JwtService;
import project.backend.domain.member.entity.Member;
import project.backend.global.error.exception.BusinessException;
import project.backend.global.error.exception.ErrorCode;
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
    private final HashtagService hashtagService;
    private final JwtService jwtService;
    private final CategoryService categoryService;

    public Feed createFeed(FeedPostRequestDto feedPostRequestDto, String accessToken) {

        Feed feed = Feed.builder()
                .title(feedPostRequestDto.getTitle())
                .content(feedPostRequestDto.getContent())
                .imageUrl1(feedPostRequestDto.getImageUrl1())
                .imageUrl2(feedPostRequestDto.getImageUrl2())
                .imageUrl3(feedPostRequestDto.getImageUrl3())
                .latitude(feedPostRequestDto.getLatitude())
                .longitude(feedPostRequestDto.getLongitude())
                .build();

        Category category = categoryService.verifiedCategoryName(feedPostRequestDto.getCategory());
        Member member = jwtService.getMemberFromAccessToken(accessToken);
        Hashtag hashtag = hashtagService.createHashtag(HashtagPostRequestDto.builder().name(feedPostRequestDto.getHashtag()).build());

        feed.setCategory(category);
        feed.setMember(member);
        feed.setHashtag(hashtag);

        feedRepository.save(feed);
        return feed;
    }

    @Transactional(readOnly = true)
    public Feed getFeed(Long id) {
        return verifiedFeed(id);
    }

    @Transactional(readOnly = true)
    public List<Feed> getFeedList() {
        return feedRepository.findAll();
    }

    public Feed patchFeed(Long id, FeedPatchRequestDto feedPatchRequestDto, String accessToken) {
        Member member = jwtService.getMemberFromAccessToken(accessToken);
        Feed feed = verifiedFeed(id).patchFeed(feedPatchRequestDto);
        if (feed.getMember() == member) {
            feed.patchFeed(feedPatchRequestDto);
            return feedRepository.save(feed);
        } else {
            throw new BusinessException(ErrorCode.FEED_PATCH_FAIL);
        }
    }

    public void deleteFeed(String accessToken, Long id) {
        Member member = jwtService.getMemberFromAccessToken(accessToken);
        if (verifiedFeed(id).getMember() == member) {
            feedRepository.delete(verifiedFeed(id));
        } else {
            throw new BusinessException(ErrorCode.FEED_DELETE_FAIL);
        }

    }

    @Transactional(readOnly = true)
    public Feed verifiedFeed(Long id) {
        return feedRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.FEED_NOT_FOUND));
    }

}
