package com.springboot.domain.dailyPlan.entity;

import com.springboot.domain.BaseTimeEntity;
import com.springboot.domain.member.entity.Member;
import com.springboot.domain.toDo.entity.ToDo;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
//@Setter
@NoArgsConstructor
@Entity
public class DailyPlan extends BaseTimeEntity {

    @Id
    @Column(name = "DAILYPLAN_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "dailyPlan")
    private List<ToDo> toDos = new ArrayList<>();

    //MONTH는 h2 예약어
    @Column(nullable = false)
    private String yearMonth;

    @Column(nullable = false)
    private int date;

    @Builder
    public DailyPlan(Member member, String yearMonth, int date) {
        this.member = member;
        this.yearMonth = yearMonth;
        this.date = date;
    }
}
