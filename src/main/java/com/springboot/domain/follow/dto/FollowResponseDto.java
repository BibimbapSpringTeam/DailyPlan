package com.springboot.domain.follow.dto;

import com.springboot.domain.follow.entity.Follow;
import com.springboot.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowResponseDto {
    private Long id;
    private Long memberId;
    private Long followMemberId;

    @Builder
    public FollowResponseDto(Follow entity) {
        this.id = entity.getId();
        this.memberId = entity.getMember().getId();
        this.followMemberId = entity.getFollowMember().getId();
    }
}
