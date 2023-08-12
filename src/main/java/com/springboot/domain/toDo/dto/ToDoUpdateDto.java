package com.springboot.domain.toDo.dto;

import com.springboot.domain.category.entity.Category;
import com.springboot.domain.category.entity.CategoryCode;
import com.springboot.domain.toDo.entity.ToDo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ToDoUpdateDto {

    private String title;

    private String alarmStartTime;

    private String alarmEndTime;

    private String afterCategoryCode;

    @Builder
    public ToDoUpdateDto(ToDo entity) {
        this.title = entity.getTitle();
        this.alarmStartTime = entity.getAlarmStartTime();
        this.alarmEndTime = entity.getAlarmEndTime();
    }
}
