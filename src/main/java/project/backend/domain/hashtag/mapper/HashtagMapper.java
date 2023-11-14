package project.backend.domain.hashtag.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import project.backend.domain.hashtag.dto.HashtagPatchRequestDto;
import project.backend.domain.hashtag.dto.HashtagPostRequestDto;
import project.backend.domain.hashtag.dto.HashtagResponseDto;
import project.backend.domain.hashtag.entity.Hashtag;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HashtagMapper {
    Hashtag hashtagPostRequestDtoToHashtag(HashtagPostRequestDto hashtagPostRequestDto);

    Hashtag hashtagPatchRequestDtoToHashtag(HashtagPatchRequestDto hashtagPatchRequestDto);

    HashtagResponseDto hashtagToHashtagResponseDto(Hashtag hashtag);

    List<HashtagResponseDto> hashtagsToHashtagResponseDtos(List<Hashtag> hashtag);
}
