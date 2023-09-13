package com.springboot.domain.category.dto;

import com.springboot.domain.category.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Getter
@NoArgsConstructor
public class CategoryResponseDto {

    private Long id;

    private String title;

    private String code;

    private int countByToDo;

    private int successToDoCount;

    @Builder
    public CategoryResponseDto(Category entity) {
        this.id = entity.getId();;
        this.title = entity.getCategoryCode().getTitle();
        this.code = entity.getCategoryCode().getCode();
        this.countByToDo = entity.getCountByToDo();
        this.successToDoCount = entity.getSuccessToDoCount();
    }
}
