package com.springboot.domain.category.entity;

import com.springboot.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, BigInteger> {

    List<Category> findAllByMemberId(Long memberId);

    Optional<Category> findByMemberIdAndCategoryCode(BigInteger memberId, CategoryCode categoryCode);

}
