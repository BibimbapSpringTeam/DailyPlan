package com.springboot.domain.category.controller;

import com.springboot.domain.category.dto.CategoryResponseDto;
import com.springboot.domain.category.entity.Category;
import com.springboot.domain.category.service.CategoryService;
import com.springboot.domain.dailyPlan.dto.DailyPlanResponseDto;
import com.springboot.domain.member.dto.MemberResponseDto;
import com.springboot.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

import static com.springboot.global.result.ResultCode.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/list/{memberId}")
    public ResponseEntity<ResultResponse> getCategoryList(@PathVariable Long memberId) {
        List<CategoryResponseDto> responseDto = categoryService.getAllList(memberId);
        return ResponseEntity.ok(ResultResponse.of(GET_CATEGORY_LIST_SUCCESS, responseDto));
    }

    @GetMapping("/best3/{memberId}")
    public ResponseEntity<ResultResponse> getBest3(@PathVariable Long memberId) {
        List<CategoryResponseDto> responseDto = categoryService.getBest3(memberId);
        return ResponseEntity.ok(ResultResponse.of(GET_BEST3_TODO_SUCCESS, responseDto));
    }

    @GetMapping("/worst3/{memberId}")
    public ResponseEntity<ResultResponse> getWorst3(@PathVariable Long memberId) {
        List<CategoryResponseDto> responseDto = categoryService.getWorst3(memberId);
        return ResponseEntity.ok(ResultResponse.of(GET_WORST3_TODO_SUCCESS, responseDto));
    }
}
