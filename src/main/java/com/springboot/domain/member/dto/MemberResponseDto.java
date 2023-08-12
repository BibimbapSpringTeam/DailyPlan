package com.springboot.domain.member.dto;

import com.springboot.domain.category.entity.Category;
import com.springboot.domain.dailyPlan.entity.DailyPlan;
import com.springboot.domain.follow.entity.Follow;
import com.springboot.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

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
