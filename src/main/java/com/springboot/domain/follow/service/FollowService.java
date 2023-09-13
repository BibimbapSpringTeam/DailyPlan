package com.springboot.domain.follow.service;

import com.springboot.domain.dailyPlan.exception.EntityAlreadyExistException;
import com.springboot.domain.follow.dto.FollowResponseDto;
import com.springboot.domain.follow.entity.Follow;
import com.springboot.domain.follow.entity.FollowRepository;
import com.springboot.domain.follow.exception.FollowNotFoundException;
import com.springboot.domain.member.entity.Member;
import com.springboot.domain.member.entity.MemberRepository;
import com.springboot.global.error.exception.EntityNotFoundException;
import com.springboot.global.error.exception.FirebaseMessagingException;
import com.springboot.global.firebase.FCMService;
import com.springboot.global.firebase.dto.FCMNotificationRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

import static com.springboot.global.error.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final FCMService fcmService;

    @Transactional
    public FollowResponseDto follow(Long memberId, Long followingMemberId) throws com.google.firebase.messaging.FirebaseMessagingException {
        //팔로우 거는 A, 팔로우 받는 B DB에 존재하는지 검증
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND, "해당 id의 유저가 없습니다 : " + memberId));

        Member followMember = memberRepository.findById(followingMemberId)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND, "해당 id의 유저가 없습니다 : " + followingMemberId));

        if (followRepository.existsFollowByMemberAndFollowMember(member, followMember))
            throw new EntityAlreadyExistException(FOLLOW_ALREADY_EXIST, "이미 팔로우 하셨습니다. : " + member.getId() + " to " + followMember.getId());

        //B에 FCMNotification
        if (!fcmService.sendNotificationMessageByToken(FCMNotificationRequestDto.builder().
                targetMemberId(followMember.getId()).build())) {
            throw new FirebaseMessagingException(PUSH_NOTIFICATION_FAIL, "메세지 발송에 실패했습니다. " + followingMemberId);
        }

        Follow follow = Follow.builder()
                .member(member)
                .followMember(followMember)
                .build();

        //follow를 DB에 추가
        return FollowResponseDto.builder()
                .entity(followRepository.save(follow))
                .build();
    }

    @Transactional
    public List<FollowResponseDto> getFollowingList(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND, "해당 id를 가진 member가 없습니다. id=" + memberId));

        List<FollowResponseDto> followingList = followRepository.findByMember(member)
                .stream()
                .map(FollowResponseDto::new)
                .collect(Collectors.toList());

        if (followingList.isEmpty())
            throw new FollowNotFoundException(FOLLOW_LIST_NOT_FOUND, "해당 멤버가 팔로우한 멤버가 없습니다: " + memberId);
        return followingList;
    }

    @Transactional
    public List<FollowResponseDto> getFollowers(Long followingMemberId) {
        Member member = memberRepository.findById(followingMemberId)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND, "해당 id를 가진 member가 없습니다. id=" + followingMemberId));

        List<FollowResponseDto> followerList = followRepository.findByMember(member)
                .stream()
                .map(FollowResponseDto::new)
                .collect(Collectors.toList());

        if (followerList.isEmpty())
            throw new FollowNotFoundException(FOLLOW_LIST_NOT_FOUND, "해당 멤버가 팔로우한 멤버가 없습니다: " + followingMemberId);
        return followerList;
    }

    @Transactional
    public boolean unfollow(Long memberId, Long followingMemberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND, "해당 id의 유저가 없습니다 : " + memberId));

        Member followMember = memberRepository.findById(followingMemberId)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND, "해당 id의 유저가 없습니다 : " + followingMemberId));

        if (followRepository.existsFollowByMemberAndFollowMember(member, followMember))
            followRepository.delete(followRepository.findByMemberAndFollowMember(member, followMember));

        return true;
    }

}
