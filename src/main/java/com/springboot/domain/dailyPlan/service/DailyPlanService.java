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

    @Transactional
    public DailyPlanResponseDto post(BigInteger memberId, String date) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND, "해당 id를 가진 member가 없습니다. id=" + memberId));

        if(member.getDailyPlans().stream()
                .anyMatch(dailyPlan -> dailyPlan.getDate().equals(date)))
            throw new EntityAlreadyExistException(DAILYPLAN_ALREADY_EXIST, "해당 날짜에 데일리 플랜이 이미 존재합니다, PUT으로 update해주세요 : " + date);

        return new DailyPlanResponseDto(dailyPlanRepository.save(new DailyPlan(date, member)));
    }

    @Transactional
    public DailyPlanResponseDto get(Long dailyPlanId) {
        DailyPlan entity = dailyPlanRepository.findById(dailyPlanId)
                .orElseThrow(() -> new EntityNotFoundException(DAILYPLAN_NOT_FOUND, "해당 id를 가진 dailyPlan이 없습니다. id=" + dailyPlanId));

        return new DailyPlanResponseDto(entity);
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

        List<DailyPlanResponseDto> list = member.getDailyPlans().stream()
                //filter로 같은 월에 작성된(YearMonth) 플랜만 list에 저장
                .filter(entity -> entity.getYearMonth().equals(yearMonth))
                .map(entity ->  DailyPlanResponseDto.builder()
                            .entity(entity)
                            .build())
                        .collect(Collectors.toList());
        if(list.isEmpty()) {
            throw new DailyPlannerNotFoundException(DAILYPLAN_MONTHLIST_NOT_FOUND, "해당 월에 작성된 데일리 플래너가 없습니다 : " + yearMonth);
        }
        return list;
    }
}
