package com.springboot.domain.member.service;

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
        return new MemberResponseDto(memberRepository.save(requestDto.toEntity()));
    }

    @Transactional
    public MemberResponseDto get(BigInteger memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND, "해당 id를 가진 member가 없습니다. id=" + memberId));

        return new MemberResponseDto(member);
    }

    @Transactional
    public MemberResponseDto getEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND, "해당 email을 가진 member가 없습니다. email=" + email));

        return new MemberResponseDto(member);
    }
}
