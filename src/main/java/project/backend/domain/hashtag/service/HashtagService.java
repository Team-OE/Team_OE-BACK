package project.backend.domain.hashtag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.backend.domain.hashtag.dto.HashtagPatchRequestDto;
import project.backend.domain.hashtag.dto.HashtagPostRequestDto;
import project.backend.domain.hashtag.entity.Hashtag;
import project.backend.domain.hashtag.mapper.HashtagMapper;
import project.backend.domain.hashtag.repository.HashtagRepository;
import project.backend.global.error.exception.BusinessException;
import project.backend.global.error.exception.ErrorCode;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HashtagService {
    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;

    public Hashtag createHashtag(HashtagPostRequestDto hashtagPostRequestDto){
        Hashtag hashtag = Hashtag.builder()
                .name(hashtagPostRequestDto.getName()).build();
        hashtagRepository.save(hashtag);
        return hashtag;
    }

    public Hashtag getHashtag(Long id) {
        return verifiedHashtag(id);
    }

    public List<Hashtag> getHashtagList() {
        return hashtagRepository.findAll();
    }

    public Hashtag patchHashtag(Long id, HashtagPatchRequestDto hashtagPatchRequestDto) {
        Hashtag hashtag = verifiedHashtag(id).patchHashtag(hashtagPatchRequestDto);
        hashtagRepository.save(hashtag);
        return hashtag;
    }

    public void deleteHashtag(Long id) {
        hashtagRepository.delete(verifiedHashtag(id));
    }

    private Hashtag verifiedHashtag(Long id) {
        return hashtagRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
    }

}
