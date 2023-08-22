package com.springboot.domain.member.entity;

import com.springboot.domain.BaseTimeEntity;
import com.springboot.domain.category.entity.Category;
import com.springboot.domain.dailyPlan.entity.DailyPlan;
import com.springboot.domain.follow.entity.Follow;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @OneToMany(mappedBy = "member")
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<DailyPlan> dailyPlans = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Follow> follows = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String profileUrl;

    private long planSuccessCount;

    @Builder
    public Member(String name, String email, String profileUrl, int planSuccessCount) {
        this.name = name;
        this.email = email;
        this.profileUrl = profileUrl;
        this.planSuccessCount = planSuccessCount;
    }

    public Member update(String name, String picture){
        this.name = name;
        this.profileUrl = picture;
        return this;
    }
}
