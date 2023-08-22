package com.springboot.domain.dailyPlan.dto;

import com.springboot.domain.dailyPlan.entity.DailyPlan;
import lombok.Builder;
import lombok.Getter;


@Getter
public class DailyPlanResponseDto {

    private Long id;
    private String yearMonth;
    private int date;

    @Builder
    public DailyPlanResponseDto(DailyPlan entity) {
        this.id = entity.getId();
        this.yearMonth = entity.getYearMonth();
        this.date = entity.getDate();
    }
}
