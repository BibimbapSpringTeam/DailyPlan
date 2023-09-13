package com.springboot.domain.member.service;

import com.springboot.domain.member.dto.MemberFirebaseTokenRequestDto;
import com.springboot.domain.member.dto.MemberRequestDto;
import com.springboot.domain.member.dto.MemberResponseDto;
import com.springboot.domain.member.entity.Member;
import com.springboot.domain.member.entity.MemberRepository;
import com.springboot.global.error.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

import static com.springboot.global.error.exception.ErrorCode.MEMBER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    @Transactional
    public MemberResponseDto post(MemberRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .map(entity -> entity.update(requestDto.getName(), requestDto.getProfileUrl()))
                .orElse(requestDto.toEntity());

        return MemberResponseDto.builder()
                .entity(memberRepository.save(member))
                .build();
    }

    @Transactional
    public MemberResponseDto get(Long memberId) {
        return memberRepository.findById(memberId)
                .map(MemberResponseDto::new)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND, "해당 id의 유저가 없습니다 : " + memberId));
    }

    @Transactional
    public MemberResponseDto getEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND, "해당 email을 가진 member가 없습니다. email=" + email));

        return new MemberResponseDto(member);
    }

    @Transactional
    public boolean saveOrUpdateFireBaseTokenByMemberId(Long memberId, MemberFirebaseTokenRequestDto requestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND, "해당 멤버가 존재하지 않습니다 : " + memberId));
        member.setFirebaseToken(requestDto.getFirebaseToken());
        return true;
    }
}
