package com.springboot.domain.toDo;

import com.springboot.domain.category.entity.CategoryRepository;
import com.springboot.domain.dailyPlan.entity.DailyPlanRepository;
import com.springboot.domain.toDo.dto.ToDoRequestDto;
import com.springboot.domain.toDo.dto.ToDoResponseDto;
import com.springboot.domain.toDo.dto.ToDoUpdateDto;
import com.springboot.domain.toDo.entity.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ToDoService {
    
    private final DailyPlanRepository dailyPlanRepository;
    
    private final ToDoRepository toDoRepository;
    
    private final CategoryRepository categoryRepository;
    
    @Transactional
    public long post(Long dailyPlanId, ToDoRequestDto requestDto) {
    }
    
    
    public ToDoResponseDto findById(Long todoId) {
    }

    @Transactional
    public boolean update(Long todoId, ToDoUpdateDto updateDto) {
    }

    public boolean delete(Long todoId) {
    }

    public List<ToDoResponseDto> getToDoList(Long dailyPlanId) {
    }

    public boolean complete(Long todoId) {
    }
}
