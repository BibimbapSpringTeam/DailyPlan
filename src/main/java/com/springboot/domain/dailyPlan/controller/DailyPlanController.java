package com.springboot.domain.dailyPlan.controller;

import com.springboot.domain.dailyPlan.dto.DailyPlanListResponseDto;
import com.springboot.domain.dailyPlan.dto.DailyPlanResponseDto;
import com.springboot.domain.dailyPlan.service.DailyPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dailyplan")
public class DailyPlanController {

    private final DailyPlanService dailyPlanService;

    @PostMapping("/{member_id}/{date}")
    public Long post(@PathVariable Long member_id, String date) {
        return dailyPlanService.post(member_id, date);
    }

    @GetMapping("/{dailyPlanId}")
    public DailyPlanResponseDto get(@PathVariable Long dailyPlanId) {
        return dailyPlanService.get(dailyPlanId);
    }

    @DeleteMapping("/{dailyPlanId}")
    public Long delete(@PathVariable Long dailyPlanId) {
        dailyPlanService.delete(dailyPlanId);
        return dailyPlanId;
    }

    @GetMapping("/{memberId}/{yearMonth}")
    public List<DailyPlanListResponseDto> getList(@PathVariable Long memberId, @PathVariable String yearMonth) {
        return dailyPlanService.getList(memberId, yearMonth);
    }
}
