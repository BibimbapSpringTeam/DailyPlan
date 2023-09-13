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
        Member member1 = Member.builder()
                .name("John Doe")
                .email("john@example.com")
                .profileUrl("profile.jpg")
                .planSuccessCount(10)
//                .challengeSuccessCount(5)
                .firebaseToken("caZJbB4hS1uirQ0HiYaKvB:APA91bFWcND0BTRoDudm4r_TMZxu0WSZTajSdvrfn2fSc4wOjQ5mkzrvd8kMVYb7L65lbCHE8MutNrTfHwLKT4TsPRK_Stdh7iWhDW80p3hF_DmEGarzAx7f8B9j_sMFv1tjnpLjCQsd")
                .build();
        Member savedMember = memberRepository.save(member1);

        memberRepository.save(Member.builder()
                .name("Sean Kim")
                .email("seankim0@naver.com")
                .profileUrl("Kim.jpg")
                .planSuccessCount(15)
//                .challengeSuccessCount(5)
                .firebaseToken("c5HynNSxStSNR8AcTwgl1A:APA91bEqS2NL_9EWXK-RPliC7TCk7cv1Q86RcSVvO7yCXmouMf4-0cTE5nCsFnryS7CpgQj0BnnWIYo2Ogx-1yaYSK_4x6DQdS2WYas3rc55Y-3BYkRrGuRkXHh5fqfr6ttNez6SlwQe")
                .build());

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