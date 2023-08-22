package com.springboot.domain.dailyPlan.service;

import com.springboot.domain.dailyPlan.dto.DailyPlanResponseDto;
import com.springboot.domain.dailyPlan.entity.DailyPlan;
import com.springboot.domain.dailyPlan.entity.DailyPlanRepository;
import com.springboot.domain.dailyPlan.exception.DailyPlannerNotFoundException;
import com.springboot.domain.dailyPlan.exception.EntityAlreadyExistException;
import com.springboot.domain.member.entity.Member;
import com.springboot.domain.member.entity.MemberRepository;
import com.springboot.global.error.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import static com.springboot.global.error.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class DailyPlanService {
    private final DailyPlanRepository dailyPlanRepository;
    private final MemberRepository memberRepository;

    //substring에 사용되는 상수들을 미리 선언하여 사용
    private final int YEAR_MONTH_START_INDEX = 0;
    private final int DAY_START_INDEX = 6;
    private final int DATE_END_INDEX = 8;
    @Transactional
    public Long post(BigInteger memberId, String day) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND, "해당 id를 가진 member가 없습니다. id=" + memberId));

        String yearMonth =day.substring(YEAR_MONTH_START_INDEX, DAY_START_INDEX);
        int date = Integer.parseInt(day.substring(DAY_START_INDEX, DATE_END_INDEX));

        if(dailyPlanRepository.existsDailyPlanByYearMonthAndDate(yearMonth, date))
            throw new EntityAlreadyExistException(DAILYPLAN_ALREADY_EXIST, "해당 날짜에 데일리 플랜이 이미 존재합니다, PUT으로 update해주세요 : " + date);

        DailyPlan dailyPlan = DailyPlan.builder()
                .member(member)
                .yearMonth(yearMonth)
                .date(date)
                .build();
        return dailyPlanRepository.save(dailyPlan).getId();
    }

    @Transactional
    public DailyPlanResponseDto get(Long dailyPlanId) {
        return dailyPlanRepository.findById(dailyPlanId)
                .map(DailyPlanResponseDto::new)
                .orElseThrow(() -> new EntityNotFoundException(DAILYPLAN_NOT_FOUND, "해당 데일리 플랜이 존재하지 않습니다 : " + dailyPlanId));
    }

    @Transactional
    public boolean delete(Long dailyPlanId) {
        DailyPlan dailyPlan = dailyPlanRepository.findById(dailyPlanId)
                .orElseThrow(() -> new EntityNotFoundException(DAILYPLAN_NOT_FOUND, "해당 id를 가진 dailyPlan이 없습니다. id=" + dailyPlanId));

        dailyPlanRepository.delete(dailyPlan);
        return true;
    }

    @Transactional
    public List<DailyPlanResponseDto> getList(BigInteger memberId, String yearMonth) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND, "해당 id를 가진 member가 없습니다. id=" + memberId));

        List<DailyPlanResponseDto> list = dailyPlanRepository.findByYearMonth(yearMonth)
                .stream()
                .map(DailyPlanResponseDto::new)
                .collect(Collectors.toList());

        if(list.isEmpty()) {
            throw new DailyPlannerNotFoundException(DAILYPLAN_MONTHLIST_NOT_FOUND, "해당 월에 작성된 데일리 플래너가 없습니다 : " + yearMonth);
        }
        return list;
    }
}
