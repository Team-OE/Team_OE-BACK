package project.backend.domain.member.service;

import project.backend.domain.member.dto.MemberPatchRequestDto;
import project.backend.domain.member.entity.Member;
import project.backend.domain.member.entity.SocialType;
import project.backend.domain.member.repository.MemberRepository;
import project.backend.global.error.exception.BusinessException;
import project.backend.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * socialId와 socialType 기준 Member 반환
     *
     * @param socialId
     * @param socialType
     * @return Member
     */
    public Member getMemberBySocial(String socialId, SocialType socialType) {
        return memberRepository.findFirstBySocialIdAndSocialType(socialId, socialType)
                .orElseGet(() -> createMember(socialId, socialType));
    }

    /**
     * socialId와 socialType를 가지고 있는 Member 생성
     *
     * @param socialId
     * @param socialType
     * @return Memeber
     */
    public Member createMember(String socialId, SocialType socialType) {
        Member member = Member.builder()
                .socialId(socialId)
                .socialType(socialType).build();
        memberRepository.save(member);
        return member;
    }

    @Transactional(readOnly = true)
    public Member getMemberBySocialIdAndSocialType(String socialId, SocialType socialType) {
        return memberRepository.findFirstBySocialIdAndSocialType(socialId, socialType).orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Member getMember(Long id) {
        return verifiedMember(id);
    }

    @Transactional(readOnly = true)
    public List<Member> getMemberList() {
        return memberRepository.findAll();
    }

    public Member patchMember(Long id, MemberPatchRequestDto memberPatchRequestDto) {
        if (memberPatchRequestDto.getNickname() != null) {
            verifiedNickname(memberPatchRequestDto.getNickname());
        }
        Member member = verifiedMember(id).patchMember(memberPatchRequestDto);
        memberRepository.save(member);
        return member;
    }

    public void deleteMember(Long id) {
        memberRepository.delete(verifiedMember(id));
    }

    /**
     * 닉네임 중복 검사
     *
     * @param nickname
     * @return
     */
    @Transactional(readOnly = true)
    public void verifiedNickname(String nickname) {
        if (nickname != null && memberRepository.findAllByNickname(nickname).size() > 0) {
            throw new BusinessException(ErrorCode.NICKNAME_DUPLICATE);
        }
    }

    /**
     * Member 유효성 검사
     * @param id
     * @return
     */
    public Member verifiedMember(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }

}
