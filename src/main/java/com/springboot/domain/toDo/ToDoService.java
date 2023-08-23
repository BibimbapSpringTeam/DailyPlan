package com.springboot.domain.toDo;

import com.springboot.domain.category.entity.Category;
import com.springboot.domain.category.entity.CategoryCode;
import com.springboot.domain.category.entity.CategoryRepository;
import com.springboot.domain.category.exception.CodeNotFoundException;
import com.springboot.domain.category.service.CategoryService;
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

    private final CategoryService categoryService;

    @Transactional
    public long post(Long dailyPlanId, ToDoRequestDto requestDto) {
        ToDo toDo = requestDto.toEntity();

        DailyPlan dailyPlan = dailyPlanRepository.findById(dailyPlanId)
                .orElseThrow(() -> new EntityNotFoundException(DAILYPLAN_NOT_FOUND, "해당 id에 해당하는 데일리 플랜이 없습니다 : " + dailyPlanId));

        Category category = categoryService.save(requestDto.getCategoryCode(), dailyPlan.getMember());

        toDo.setDailyPlan(dailyPlan);
        toDo.setCategory(category);
//        Member member = dailyPlan.getMember();
//        CategoryCode code = CategoryCode.find(requestDto.getCategoryCode());
        // 카테고리 존재 확인하는 함수 -> categoryService.save함수에서 자동으로 실행됨

        return toDoRepository.save(toDo).getId();
    }

//    private Category findCategoryFromMember(CategoryCode code, Member member) {
//
//        Category category = null;
//        // 카테고리가 존재하지 않을 시 생성
//        if(!member.getCategories().stream()
//                .anyMatch(c -> c.getCategoryCode().equals(code))) {
//            category = categoryRepository.save(new Category(member, code));
//        } else {
//            //카테고리가 존재할 시 해당 코드 카테고리 찾기
//            List<Category> categoryList = member.getCategories().stream()
//                    .filter(entity -> entity.getCategoryCode().equals(code))
//                    .collect(Collectors.toList());
//            if(!categoryList.isEmpty()) {
//                category = categoryList.get(0);
//                //카테고리 투두 + 1
//                category.setCountByToDo(category.getCountByToDo().add(BigInteger.ONE));
//            }
//        }
//        return category;
//    }

    @Transactional
    public ToDoResponseDto findById(Long todoId) {

        ToDo toDo = toDoRepository.findById(todoId)
                .orElseThrow(() -> new EntityNotFoundException(TODOLIST_NOT_FOUND, "해당 Id에 해당하는 투두리스트가 없습니다 : " + todoId));

        return new ToDoResponseDto(toDo);
    }

    @Transactional
    public boolean update(Long todoId, ToDoUpdateDto updateDto) {

        ToDo toDo = toDoRepository.findById(todoId)
                .map(entity -> entity.update(updateDto))
                .orElseThrow(() -> new EntityNotFoundException(TODOLIST_NOT_FOUND, "해당 Id에 해당하는 투두리스트가 없습니다 : " + todoId));

        //upadate로 들어온 코드가 valid한지 확인
        CategoryCode code = CategoryCode.find(updateDto.getAfterCategoryCode());

        Category category = toDo.getCategory();
        DailyPlan dailyPlan = toDo.getDailyPlan();
        Member member = dailyPlan.getMember();

        //update로 들어온 코드와 toDo에 저장되어있는 코드가 동일하지 않을 시에만 업데이트
        if(category.getCategoryCode() != code) {
//            //현재 카테고리의 toDoCount 줄이기
//            beforeCategory.setCountByToDo(beforeCategory.getCountByToDo().subtract(BigInteger.ONE));
//
//            Category category = findCategoryFromMember(code, member);
//            //현재 카테고리가 success 였을 때 successToDoCount 줄이기
//            if (toDo.isComplete()) {
//                beforeCategory.setSuccessToDoCount(beforeCategory.getSuccessToDoCount().subtract(BigInteger.ONE));
//                category.setSuccessToDoCount(category.getSuccessToDoCount().add(BigInteger.ONE));
//            }
//
//            if (beforeCategory.getCountByToDo() == BigInteger.ZERO) {
//                categoryRepository.delete(beforeCategory);
//            }
            category = categoryService.update(
                    category, code.toString() );

        }
        toDo.setCategory(category);

        return true;
    }


    @Transactional
    public boolean delete(Long todoId) {

        ToDo toDo = toDoRepository.findById(todoId)
                .orElseThrow(() -> new EntityNotFoundException(TODOLIST_NOT_FOUND, "해당 Id에 해당하는 투두리스트가 없습니다 : " + todoId));


        Category category = toDo.getCategory();

        //카테고리 투두 - 1
        category.minusCountByToDo();

        //success 였을 경우 - 1
        if (toDo.isComplete()) {
            category.cancelCompleteToDo();
        }

        if (category.getCountByToDo() == 0) {
            categoryRepository.delete(category);
        }
        toDoRepository.delete(toDo);

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


        if(!toDo.isComplete()) {
            toDo.getCategory().completeToDo();
        }
        toDo.setComplete(true);
        return true;
    }
    @Transactional
    public boolean failed(Long todoId) {
        ToDo toDo = toDoRepository.findById(todoId)
                .orElseThrow(() -> new EntityNotFoundException(TODOLIST_NOT_FOUND, "해당 Id에 해당하는 투두리스트가 없습니다 : " + todoId));

        if(toDo.isComplete()) {
            Category category = toDo.getCategory();
            category.cancelCompleteToDo();
        }
        toDo.setComplete(false);
        return true;
    }
}
