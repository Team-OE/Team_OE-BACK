package project.backend.domain.member.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import project.backend.domain.member.dto.MemberPatchRequestDto;
import project.backend.domain.member.dto.MemberPostRequestDto;
import project.backend.domain.member.dto.MemberResponseDto;
import project.backend.domain.member.dto.MemberResponseDto.MemberResponseDtoBuilder;
import project.backend.domain.member.entity.Member;
import project.backend.domain.member.entity.Member.MemberBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-14T21:56:27+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.3.jar, environment: Java 11.0.16.1 (Oracle Corporation)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member memberPostRequestDtoToMember(MemberPostRequestDto memberPostRequestDto) {
        if ( memberPostRequestDto == null ) {
            return null;
        }

        MemberBuilder member = Member.builder();

        member.socialType( memberPostRequestDto.getSocialType() );
        member.socialId( memberPostRequestDto.getSocialId() );

        return member.build();
    }

    @Override
    public Member memberPatchRequestDtoToMember(MemberPatchRequestDto memberPatchRequestDto) {
        if ( memberPatchRequestDto == null ) {
            return null;
        }

        MemberBuilder member = Member.builder();

        member.nickname( memberPatchRequestDto.getNickname() );
        member.profileUrl( memberPatchRequestDto.getProfileUrl() );
        member.refreshToken( memberPatchRequestDto.getRefreshToken() );

        return member.build();
    }

    @Override
    public MemberResponseDto memberToMemberResponseDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberResponseDtoBuilder memberResponseDto = MemberResponseDto.builder();

        memberResponseDto.id( member.getId() );
        memberResponseDto.createdDate( member.getCreatedDate() );
        memberResponseDto.updatedDate( member.getUpdatedDate() );

        return memberResponseDto.build();
    }

    @Override
    public List<MemberResponseDto> membersToMemberResponseDtos(List<Member> member) {
        if ( member == null ) {
            return null;
        }

        List<MemberResponseDto> list = new ArrayList<MemberResponseDto>( member.size() );
        for ( Member member1 : member ) {
            list.add( memberToMemberResponseDto( member1 ) );
        }

        return list;
    }
}
