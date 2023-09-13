package com.springboot.domain.toDo.controller;

import com.springboot.domain.toDo.ToDoService;
import com.springboot.domain.toDo.dto.ToDoRequestDto;
import com.springboot.domain.toDo.dto.ToDoResponseDto;
import com.springboot.domain.toDo.dto.ToDoUpdateDto;
import com.springboot.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.springboot.global.result.ResultCode.*;

@RequiredArgsConstructor
@RestController
public class ToDoController {

    @Autowired
    private final ToDoService toDoService;

    @PostMapping("/todo/{dailyPlanId}")
    public ResponseEntity<ResultResponse> save(@PathVariable Long dailyPlanId, @RequestBody ToDoRequestDto requestDto) {
        Long todoId = toDoService.post(dailyPlanId, requestDto);
        return ResponseEntity.ok(ResultResponse.of(SAVE_TODO_SUCCESS, todoId));
    }

    @GetMapping("/todo/{todoId}")
    public ResponseEntity<ResultResponse> findById(@PathVariable Long todoId) {
        ToDoResponseDto responseDto = toDoService.findById(todoId);
        return ResponseEntity.ok(ResultResponse.of(GET_TODO_SUCCESS, responseDto));
    }

    @PutMapping("/todo/{todoId}")
    public ResponseEntity<ResultResponse> update(@PathVariable Long todoId, @RequestBody ToDoUpdateDto updateDto) {
        boolean success = toDoService.update(todoId, updateDto);
        return ResponseEntity.ok(ResultResponse.of(UPDATE_TODO_SUCCESS, success));
    }

    @DeleteMapping("/todo/{todoId}")
    public ResponseEntity<ResultResponse> delete(@PathVariable Long todoId) {
        boolean success = toDoService.delete(todoId);
        return ResponseEntity.ok(ResultResponse.of(DELETE_TODO_SUCCESS, success));
    }

    @GetMapping("/todo/{dailyPlanId}/list")
    public ResponseEntity<ResultResponse> getList(@PathVariable Long dailyPlanId) {
        List<ToDoResponseDto> responseDtos = toDoService.getToDoList(dailyPlanId);
        return ResponseEntity.ok(ResultResponse.of(GET_TODO_ALL_SUCCESS, responseDtos));
    }

    @PostMapping("/todo/complete/{todoId}")
    public ResponseEntity<ResultResponse> completeTodo(@PathVariable Long todoId) {
        boolean success = toDoService.complete(todoId);
        return ResponseEntity.ok(ResultResponse.of(COMPLETE_TODO, success));
    }

    @PostMapping("/todo/fail/{todoId}")
    public ResponseEntity<ResultResponse> failTodo(@PathVariable Long todoId) {
        boolean success = toDoService.failed(todoId);
        return ResponseEntity.ok(ResultResponse.of(FAIL_TODO, success));
    }

}
