package com.springboot.domain.dailyPlan.service;

import com.springboot.domain.dailyPlan.dto.DailyPlanListResponseDto;
import com.springboot.domain.dailyPlan.dto.DailyPlanResponseDto;
import com.springboot.domain.dailyPlan.entity.DailyPlan;
import com.springboot.domain.dailyPlan.entity.DailyPlanRepository;
import com.springboot.domain.member.entity.Member;
import com.springboot.domain.member.entity.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DailyPlanService {
    private final DailyPlanRepository dailyPlanRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long post(Long memberId, String date) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 member가 없습니다. id=" + memberId));

        return dailyPlanRepository.save(new DailyPlan(date, member)).getId();
    }

    @Transactional
    public DailyPlanResponseDto get(Long dailyPlanId) {
        DailyPlan entity = dailyPlanRepository.findById(dailyPlanId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 데일리 플랜이 없습니다. id="+ dailyPlanId));

        return new DailyPlanResponseDto(entity);
    }

    @Transactional
    public void delete(Long dailyPlanId) {
        DailyPlan dailyPlan = dailyPlanRepository.findById(dailyPlanId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 데일리 플랜이 없습니다. id="+ dailyPlanId));
        dailyPlanRepository.delete(dailyPlan);
    }

    @Transactional
    public List<DailyPlanListResponseDto> getList(Long memberId, String yearMonth) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 member가 없습니다. id=" + memberId));

        List<DailyPlanListResponseDto> list = member.getDailyPlans().stream()
                //filter로 같은 월에 작성된(YearMonth) 플랜만 list에 저장
                .filter(entity -> entity.getYearMonth().equals(yearMonth))
                .map(entity ->  DailyPlanListResponseDto.builder()
                            .entity(entity)
                            .build())
                        .collect(Collectors.toList());
        return list;
    }
}
