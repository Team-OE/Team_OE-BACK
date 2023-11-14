package project.backend.domain.hashtag.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import project.backend.domain.hashtag.dto.HashtagPatchRequestDto;
import project.backend.domain.hashtag.dto.HashtagPostRequestDto;
import project.backend.domain.hashtag.dto.HashtagResponseDto;
import project.backend.domain.hashtag.dto.HashtagResponseDto.HashtagResponseDtoBuilder;
import project.backend.domain.hashtag.entity.Hashtag;
import project.backend.domain.hashtag.entity.Hashtag.HashtagBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-15T01:53:24+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16.1 (Oracle Corporation)"
)
@Component
public class HashtagMapperImpl implements HashtagMapper {

    @Override
    public Hashtag hashtagPostRequestDtoToHashtag(HashtagPostRequestDto hashtagPostRequestDto) {
        if ( hashtagPostRequestDto == null ) {
            return null;
        }

        HashtagBuilder hashtag = Hashtag.builder();

        hashtag.name( hashtagPostRequestDto.getName() );

        return hashtag.build();
    }

    @Override
    public Hashtag hashtagPatchRequestDtoToHashtag(HashtagPatchRequestDto hashtagPatchRequestDto) {
        if ( hashtagPatchRequestDto == null ) {
            return null;
        }

        HashtagBuilder hashtag = Hashtag.builder();

        hashtag.name( hashtagPatchRequestDto.getName() );

        return hashtag.build();
    }

    @Override
    public HashtagResponseDto hashtagToHashtagResponseDto(Hashtag hashtag) {
        if ( hashtag == null ) {
            return null;
        }

        HashtagResponseDtoBuilder hashtagResponseDto = HashtagResponseDto.builder();

        hashtagResponseDto.name( hashtag.getName() );

        return hashtagResponseDto.build();
    }

    @Override
    public List<HashtagResponseDto> hashtagsToHashtagResponseDtos(List<Hashtag> hashtag) {
        if ( hashtag == null ) {
            return null;
        }

        List<HashtagResponseDto> list = new ArrayList<HashtagResponseDto>( hashtag.size() );
        for ( Hashtag hashtag1 : hashtag ) {
            list.add( hashtagToHashtagResponseDto( hashtag1 ) );
        }

        return list;
    }
}
