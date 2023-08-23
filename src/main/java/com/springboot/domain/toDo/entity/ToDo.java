package com.springboot.domain.toDo.entity;

import com.springboot.domain.category.entity.Category;
import com.springboot.domain.dailyPlan.entity.DailyPlan;
import com.springboot.domain.member.entity.Member;
import com.springboot.domain.toDo.dto.ToDoUpdateDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
public class ToDo {

    @Id
    @Column(name = "TODO_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "DAILYPLAN_ID")
    private DailyPlan dailyPlan;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @Column(nullable = false)
    private boolean isComplete;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String alarmStartTime;

    @Column(nullable = false)
    private String alarmEndTime;

    @Builder
    public ToDo(DailyPlan dailyPlan, String title, String alarmStartTime, String alarmEndTime, Category category) {
        this.dailyPlan = dailyPlan;
        this.isComplete = false;
        this.title = title;
        this.alarmStartTime = alarmStartTime;
        this.alarmEndTime = alarmEndTime;
        this.category = category;
    }

    public ToDo update(ToDoUpdateDto toDoUpdateDto) {
        this.title = toDoUpdateDto.getTitle();
        this.alarmStartTime = toDoUpdateDto.getAlarmStartTime();
        this.alarmEndTime = toDoUpdateDto.getAlarmEndTime();
        return this;
    }

    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDailyPlan(DailyPlan dailyPlan) {
        this.dailyPlan = dailyPlan;
    }
}
