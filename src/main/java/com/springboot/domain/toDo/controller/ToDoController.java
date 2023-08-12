package com.springboot.domain.toDo.controller;

import com.springboot.domain.dailyPlan.dto.DailyPlanListResponseDto;
import com.springboot.domain.dailyPlan.dto.DailyPlanResponseDto;
import com.springboot.domain.dailyPlan.service.DailyPlanService;
import com.springboot.domain.toDo.ToDoService;
import com.springboot.domain.toDo.dto.ToDoRequestDto;
import com.springboot.domain.toDo.dto.ToDoResponseDto;
import com.springboot.domain.toDo.dto.ToDoUpdateDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ToDoController {

        @Autowired
        private final ToDoService toDoService;

        @PostMapping("/todo/{dailyPlanId}")
        public long save(@PathVariable Long dailyPlanId, @RequestBody ToDoRequestDto requestDto) {
            return toDoService.post(dailyPlanId, requestDto);
        }

        @GetMapping("/todo/{todoId}")
        public ToDoResponseDto findById(@PathVariable Long todoId) {
            return toDoService.findById(todoId);
        }

        @PutMapping("/todo/{todoId}")
        public boolean update(@PathVariable Long todoId, @RequestBody ToDoUpdateDto updateDto) {
            return toDoService.update(todoId, updateDto);
        }

        @DeleteMapping("/todo/{todoId}")
        public Long delete(@PathVariable Long todoId) {
            toDoService.delete(todoId);
            return todoId;
        }

        @GetMapping("/todo/{dailyPlanId}/list")
        public List<ToDoResponseDto> getList(@PathVariable Long dailyPlanId) {
            return toDoService.getToDoList(dailyPlanId);
        }

        @PostMapping("/todo/complete/{todoId}")
        public boolean completeTodo(@PathVariable Long todoId) {
            return toDoService.complete(todoId);
        }

        @PostMapping("/todo/fail/{todoId}")
        public boolean failTodo(@PathVariable Long todoId) {
            return toDoService.failed(todoId);
        }

    }

}
