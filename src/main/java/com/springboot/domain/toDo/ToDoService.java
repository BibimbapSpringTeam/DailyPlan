package com.springboot.domain.toDo;

import com.springboot.domain.category.entity.Category;
import com.springboot.domain.category.entity.CategoryCode;
import com.springboot.domain.category.entity.CategoryRepository;
import com.springboot.domain.category.exception.CodeNotFoundException;
import com.springboot.domain.dailyPlan.dto.DailyPlanResponseDto;
import com.springboot.domain.dailyPlan.entity.DailyPlan;
import com.springboot.domain.dailyPlan.entity.DailyPlanRepository;
import com.springboot.domain.member.entity.Member;
import com.springboot.domain.toDo.dto.ToDoRequestDto;
import com.springboot.domain.toDo.dto.ToDoResponseDto;
import com.springboot.domain.toDo.dto.ToDoUpdateDto;
import com.springboot.domain.toDo.entity.ToDo;
import com.springboot.domain.toDo.entity.ToDoRepository;
import com.springboot.domain.toDo.exception.ToDoListNotFoundException;
import com.springboot.global.error.exception.EntityNotFoundException;
import com.springboot.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import static com.springboot.global.error.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class ToDoService {
    
    private final DailyPlanRepository dailyPlanRepository;
    
    private final ToDoRepository toDoRepository;
    
    private final CategoryRepository categoryRepository;
    
    @Transactional
    public long post(Long dailyPlanId, ToDoRequestDto requestDto) {
        ToDo toDo = requestDto.toEntity();

        DailyPlan dailyPlan = dailyPlanRepository.findById(dailyPlanId)
                .orElseThrow(() -> new EntityNotFoundException(DAILYPLAN_NOT_FOUND, "해당 id에 해당하는 데일리 플랜이 없습니다 : " + dailyPlanId));
        toDo.setDailyPlan(dailyPlan);

        Member member = dailyPlan.getMember();

        CategoryCode code = CategoryCode.find(requestDto.getCategoryCode());
        Category category = categoryRepository.save(new Category(member, code));
        toDo.setCategory(category);

        return toDoRepository.save(toDo).getId();
    }

    @Transactional
    public ToDoResponseDto findById(Long todoId) {

        ToDo toDo = toDoRepository.findById(todoId)
                .orElseThrow(() -> new EntityNotFoundException(TODOLIST_NOT_FOUND, "해당 Id에 해당하는 투두리스트가 없습니다 : " + todoId));

        return new ToDoResponseDto(toDo);
    }

    @Transactional
    public boolean update(Long todoId, ToDoUpdateDto updateDto) {

        ToDo toDo = toDoRepository.findById(todoId)
                .orElseThrow(() -> new EntityNotFoundException(TODOLIST_NOT_FOUND, "해당 Id에 해당하는 투두리스트가 없습니다 : " + todoId));


        CategoryCode code = CategoryCode.find(updateDto.getAfterCategoryCode());
        Category category = toDo.getCategory();
        category.setCategoryCode(code);

        DailyPlan dailyPlan = toDo.getDailyPlan();
        Member member = dailyPlan.getMember();

        if (!member.getCategories().contains(code)) {
            throw new CodeNotFoundException(CATEGORYCODE_NOT_FOUND,
                    "해당 유저 Id ( "+ member.getId() + " )는 "+ code.getCode() + "에 해당하는 카테고리를 가지고 있지 않습니다.");

        }

        toDo.update(updateDto.getTitle(), updateDto.getAlarmStartTime(), updateDto.getAlarmEndTime(), category);

        return true;
    }
    @Transactional
    public boolean delete(Long todoId) {

        ToDo toDo = toDoRepository.findById(todoId)
                .orElseThrow(() -> new EntityNotFoundException(TODOLIST_NOT_FOUND, "해당 Id에 해당하는 투두리스트가 없습니다 : " + todoId));

        Category category = toDo.getCategory();
        toDoRepository.delete(toDo);
        categoryRepository.delete(category);

        return true;
    }
    @Transactional
    public List<ToDoResponseDto> getToDoList(Long dailyPlanId) {
        DailyPlan dailyPlan = dailyPlanRepository.findById(dailyPlanId)
                .orElseThrow(() -> new EntityNotFoundException(DAILYPLAN_NOT_FOUND, "해당 id에 해당하는 데일리 플랜이 없습니다 : " + dailyPlanId));

        List<ToDoResponseDto> list = dailyPlan.getToDos().stream()
                .map(entity -> ToDoResponseDto.builder()
                        .entity(entity)
                        .build())
                .collect(Collectors.toList());

        if(list.isEmpty()) {
            throw new ToDoListNotFoundException(DAILYPLAN_NOT_HAVE_TODOLIST, "해당 Id의 데일리 플랜에 작성된 투두리스트가 없습니다 : " + dailyPlanId);
        }
        return list;

    }
    @Transactional
    public boolean complete(Long todoId) {
        ToDo toDo = toDoRepository.findById(todoId)
                .orElseThrow(() -> new EntityNotFoundException(TODOLIST_NOT_FOUND, "해당 Id에 해당하는 투두리스트가 없습니다 : " + todoId));

        toDo.setComplete(true);
        Category category = toDo.getCategory();
        category.setSuccessToDoCount(category.getSuccessToDoCount().add(BigInteger.valueOf(1)));
        return true;
    }
    @Transactional
    public boolean failed(Long todoId) {
        ToDo toDo = toDoRepository.findById(todoId)
                .orElseThrow(() -> new EntityNotFoundException(TODOLIST_NOT_FOUND, "해당 Id에 해당하는 투두리스트가 없습니다 : " + todoId));
        toDo.setComplete(false);
        return true;
    }
}
