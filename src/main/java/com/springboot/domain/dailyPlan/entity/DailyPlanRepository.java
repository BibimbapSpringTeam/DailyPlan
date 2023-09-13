package com.springboot.domain.dailyPlan.entity;

import com.springboot.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DailyPlanRepository extends JpaRepository<DailyPlan, Long> {

    boolean existsDailyPlanByYearMonthAndDate(String yearMonth, int date);

    List<DailyPlan> findByYearMonth(String yearMonth);
}