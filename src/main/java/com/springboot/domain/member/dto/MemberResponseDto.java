package com.springboot.domain.member.dto;

import com.springboot.domain.member.entity.Member;
import lombok.Builder;

public class MemberResponseDto {
    private Long id;
    private String name;
    private String email;
    private String profileUrl;
    private long planSuccessCount;

    @Builder
    public MemberResponseDto(Member entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.profileUrl = entity.getProfileUrl();;
        this.planSuccessCount = entity.getPlanSuccessCount();;
    }
}
