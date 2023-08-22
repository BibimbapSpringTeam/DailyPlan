package com.springboot.domain.category.service;

import com.springboot.domain.category.dto.CategoryResponseDto;
import com.springboot.domain.category.entity.Category;
import com.springboot.domain.category.entity.CategoryCode;
import com.springboot.domain.category.entity.CategoryRepository;
import com.springboot.domain.category.exception.CategoryListNotFoundException;
import com.springboot.domain.member.entity.Member;
import com.springboot.domain.member.entity.MemberRepository;
import com.springboot.global.error.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.springboot.global.error.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final MemberRepository memberRepository;

    private final CategoryRepository categoryRepository;

    public List<CategoryResponseDto> getAllList(BigInteger memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND, "해당 id를 가진 member가 없습니다. id=" + memberId));

        List<CategoryResponseDto> list = member.getCategories().stream()
                .map(entity -> CategoryResponseDto.builder()
                        .entity(entity)
                        .build())
                .collect(Collectors.toList());

        if (list.isEmpty()) {
            throw new CategoryListNotFoundException(CATEGORYLIST_NOT_FOUND, "해당 id를 가진 멤버에 카테고리 리스트가 존재하지 않습니다 : " + memberId);
        }

        return list;
    }

    public List<CategoryResponseDto> getBest3(BigInteger memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND, "해당 id를 가진 member가 없습니다. id=" + memberId));

        List<CategoryResponseDto> list = member.getCategories().stream()
                .sorted(Comparator.comparing(Category::getSuccessToDoCount).reversed())
                .limit(3)
                .map(entity -> CategoryResponseDto.builder()
                        .entity(entity)
                        .build())
                .collect(Collectors.toList());

        if (list.isEmpty()) {
            throw new CategoryListNotFoundException(CATEGORYLIST_NOT_FOUND, "해당 id를 가진 멤버에 카테고리 리스트가 존재하지 않습니다 : " + memberId);
        }
        return list;
    }

    public List<CategoryResponseDto> getWorst3(BigInteger memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND, "해당 id를 가진 member가 없습니다. id=" + memberId));

        List<CategoryResponseDto> list = member.getCategories().stream()
                .sorted(Comparator.comparing(Category::getSuccessToDoCount))
                .limit(3)
                .map(entity -> CategoryResponseDto.builder()
                        .entity(entity)
                        .build())
                .collect(Collectors.toList());

        if (list.isEmpty()) {
            throw new CategoryListNotFoundException(CATEGORYLIST_NOT_FOUND, "해당 id를 가진 멤버에 카테고리 리스트가 존재하지 않습니다 : " + memberId);
        }
        return list;
    }
    @Transactional
    public Category save(String categoryCode, Member member) {

        Category category = categoryRepository.findByMemberIdAndCategoryCode(member.getId(), CategoryCode.find(categoryCode))
                .orElse(Category.builder()
                        .categoryCode(CategoryCode.find(categoryCode))
                        .build());

        category.plusCountByToDo();

        category.setMember(member);
        return categoryRepository.save(category);
    }


    @Transactional
    public Category update(Category category, String afterCategoryCode) {
        Member member = category.getMember();

        Category newCategory = Category.builder()
                .categoryCode(CategoryCode.find(afterCategoryCode))
                .build();

        newCategory.plusCountByToDo();
        category.minusCountByToDo();

        newCategory.setMember(member);
        return categoryRepository.save(newCategory);
    }
}
