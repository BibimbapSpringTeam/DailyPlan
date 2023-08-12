package com.springboot.domain.toDo.dto;

import com.springboot.domain.category.entity.Category;
import com.springboot.domain.category.entity.CategoryCode;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ToDoResponseDto {

    private Long id;

    private String title;

    private String alarmStartTime;

    private String alarmEndTime;

    private CategoryCode categoryCode;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String alarmStartTime;

    @Column(nullable = false)
    private String alarmEndTime;

}
        "id": 1,
        "title": "string",
        "toDoStatusCode": "C",
        "alarmStartTime": "string",
        "alarmEndTime": "string",
        "categoryCode": "C001"