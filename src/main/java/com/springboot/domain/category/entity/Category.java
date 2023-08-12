package com.springboot.domain.category.entity;

import com.springboot.domain.member.entity.Member;
import com.springboot.domain.toDo.entity.ToDo;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "category")
    List<ToDo> toDos = new ArrayList<>();

    @Column(nullable = false)
    private CategoryCode categoryCode;

    @Column(nullable = false)
    private BigInteger countByToDo;

    @Column(nullable = false)
    private BigInteger successToDoCount;

    @Builder
    public Category( CategoryCode categoryCode) {
        this.categoryCode = categoryCode;
    }
}
