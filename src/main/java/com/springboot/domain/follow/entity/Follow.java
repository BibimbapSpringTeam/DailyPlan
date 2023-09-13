package com.springboot.domain.follow.entity;

import com.springboot.domain.dailyPlan.entity.DailyPlan;
import com.springboot.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "FOLLOW_MEMBER_ID")
    private Member followMember;

    @Builder
    public Follow(Member member, Member followMember)
    {
        this.member = member;
        this.followMember = followMember;
    }

}
