package project.backend.domain.comment.service;

import project.backend.domain.comment.dto.CommentPatchRequestDto;
import project.backend.domain.comment.dto.CommentPostRequestDto;
import project.backend.domain.comment.entity.Comment;
import project.backend.domain.comment.mapper.CommentMapper;
import project.backend.domain.comment.repository.CommentRepository;
import project.backend.domain.feed.entity.Feed;
import project.backend.domain.feed.service.FeedService;
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
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final JwtService jwtService;
    private final FeedService feedService;

    public Comment createComment(String accessToken, CommentPostRequestDto commentPostRequestDto){

        Member member = jwtService.getMemberFromAccessToken(accessToken);

        Feed feed = feedService.verifiedFeed(commentPostRequestDto.getFeedId());

        Comment comment = Comment.builder()
                .content(commentPostRequestDto.getContent()).build();

        comment.setMember(member);
        comment.setFeed(feed);
        commentRepository.save(comment);
        return comment;
    }

    public Comment getComment(Long id) {
        return verifiedComment(id);
    }

    public List<Comment> getCommentList() {
        return commentRepository.findAll();
    }

    public Comment patchComment(Long id, CommentPatchRequestDto commentPatchRequestDto) {
        Comment comment = verifiedComment(id).patchComment(commentPatchRequestDto);
        commentRepository.save(comment);
        return comment;
    }

    public void deleteComment(String accessToken, Long id) {
        Comment comment = verifiedComment(id);
        Member member = jwtService.getMemberFromAccessToken(accessToken);
        if (comment.getMember() == member) {
            commentRepository.delete(comment);
        } else {
            throw new BusinessException(ErrorCode.COMMENT_DELETE_FAIL);
        }
    }

    private Comment verifiedComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
    }

}
