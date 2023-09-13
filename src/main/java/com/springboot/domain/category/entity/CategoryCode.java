package com.springboot.domain.category.entity;

import com.springboot.domain.category.exception.CodeNotFoundException;
import com.springboot.global.error.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.springboot.global.error.exception.ErrorCode.CATEGORYCODE_NOT_FOUND;

@Getter
@AllArgsConstructor
public enum CategoryCode {

    DAILY("일상", "C001"),
    WORK_AND_STUDY("업무/학습", "C002"),
    MEETINGS_AND_APPOINTMENTS("모임/약속", "C003"),
    HEALTH_AND_EXERCISE("건강/운동", "C004"),
    LEISURE_AND_ENTERTAINMENT("여가/오락", "C005"),
    MANAGE_FINANCES("재정 관리", "C006"),
    OTHERS("기타", "C007"),

    ;

    private final String title;
    private final String code;

    private static final Map<String, CategoryCode> CATEGORY_CODE_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(CategoryCode::getTitle, Function.identity())));

    public static CategoryCode find(String title) {
        if (CATEGORY_CODE_MAP.containsKey(title)) {
            return CATEGORY_CODE_MAP.get(title);
        }
        throw new CodeNotFoundException(CATEGORYCODE_NOT_FOUND, title + "는 존재하지 않는 카테고리 코드 입니다.");
    }


}