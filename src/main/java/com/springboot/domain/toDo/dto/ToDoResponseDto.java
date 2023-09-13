package com.springboot.domain.toDo.dto;

import com.springboot.domain.category.entity.Category;
import com.springboot.domain.category.entity.CategoryCode;
import com.springboot.domain.toDo.entity.ToDo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ToDoResponseDto {

    private Long id;

    private String title;

    private Boolean toDoStatusCode;

    private String alarmStartTime;

    private String alarmEndTime;

    private String categoryCode;

    @Builder
    public ToDoResponseDto(ToDo entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.toDoStatusCode = entity.isComplete();
        this.alarmStartTime = entity.getAlarmStartTime();
        this.alarmEndTime = entity.getAlarmEndTime();
        this.categoryCode = entity.getCategory().getCategoryCode().getCode();
    }
}