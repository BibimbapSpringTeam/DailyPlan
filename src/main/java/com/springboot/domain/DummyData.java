package com.springboot.domain;

import com.springboot.domain.category.entity.Category;
import com.springboot.domain.category.entity.CategoryCode;
import com.springboot.domain.category.entity.CategoryRepository;
import com.springboot.domain.dailyPlan.entity.DailyPlan;
import com.springboot.domain.dailyPlan.entity.DailyPlanRepository;
import com.springboot.domain.member.entity.Member;
import com.springboot.domain.member.entity.MemberRepository;
import com.springboot.domain.toDo.entity.ToDo;
import com.springboot.domain.toDo.entity.ToDoRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
@RequiredArgsConstructor
public class DummyData {

    private final MemberRepository memberRepository;
    private final DailyPlanRepository dailyPlanRepository;
    private final ToDoRepository toDoRepository;
    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void init() {
        Member member = Member.builder()
                .name("John Doe")
                .email("john@example.com")
                .profileUrl("profile.jpg")
                .planSuccessCount(10)
//                .challengeSuccessCount(5)
//                .firebaseToken("firebaseToken")
                .build();
        Member savedMember = memberRepository.save(member);

        DailyPlan dailyPlan = DailyPlan.builder()
                .member(savedMember)
                .yearMonth("202308")
                .date(5)
                .build();
        DailyPlan savedDailyPlan = dailyPlanRepository.save(dailyPlan);

        Category category = Category.builder()
                .member(savedMember)
                .categoryCode(CategoryCode.DAILY)
                .countByToDo(1)
                .successToDoCount(0)
//                .countByChallenge(BigInteger.ZERO)
//                .successChallengeCount(0)
                .build();

        Category savedCategory = categoryRepository.save(category);

        ToDo todo = ToDo.builder()
                .dailyPlan(savedDailyPlan)
                .title("title")
                .alarmStartTime("08:30")
                .alarmEndTime("23:30")
                .category(savedCategory)
                .build();

        toDoRepository.save(todo);
    }
}