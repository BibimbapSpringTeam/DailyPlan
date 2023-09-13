package com.springboot.domain.dailyPlan.controller;

import com.springboot.domain.dailyPlan.dto.DailyPlanResponseDto;
import com.springboot.domain.dailyPlan.service.DailyPlanService;
import com.springboot.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

import static com.springboot.global.result.ResultCode.*;

@RequiredArgsConstructor
@RestController //프론트와 주고받을 때
@RequestMapping("/dailyplan")
public class DailyPlanController {

    private final DailyPlanService dailyPlanService;

    @PostMapping("/{member_id}")
    public ResponseEntity<ResultResponse> post(@PathVariable Long member_id, @RequestParam("day") String day) {
        Long savId = dailyPlanService.post(member_id, day);

        return ResponseEntity.ok(ResultResponse.of(DAILYPLAN_SAVE_SUCCESS, savId));
    }

    @GetMapping("/{dailyPlanId}")
    public ResponseEntity<ResultResponse> get(@PathVariable Long dailyPlanId) {
        DailyPlanResponseDto responseDto = dailyPlanService.get(dailyPlanId);

        return ResponseEntity.ok(ResultResponse.of(GET_DAILYPLAN_SUCCESS, responseDto));
    }

    @DeleteMapping("/{dailyPlanId}")
    public ResponseEntity<ResultResponse> delete(@PathVariable Long dailyPlanId) {
        return ResponseEntity.ok(ResultResponse.of(DELETE_DAILPLAN_SUCCESS, dailyPlanService.delete(dailyPlanId)));
    }

    @GetMapping("/{memberId}/{yearMonth}")
    public ResponseEntity<ResultResponse> getList(@PathVariable Long memberId, @PathVariable String yearMonth) {
        List<DailyPlanResponseDto> responseDto = dailyPlanService.getList(memberId, yearMonth);

        return ResponseEntity.ok(ResultResponse.of(GET_MONTH_DAILYPLAN_SUCCESS, responseDto));
    }
}
