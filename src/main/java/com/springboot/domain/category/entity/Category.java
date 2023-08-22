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

import static java.math.BigInteger.ONE;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Category {

    @Id
    @Column(name = "CATEGORY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "category")
    private List<ToDo> toDos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private CategoryCode categoryCode;

    @Column
    private BigInteger countByToDo;

    @Column
    private BigInteger successToDoCount;

    @Builder
    public Category(Member member, CategoryCode categoryCode, BigInteger countByToDo, BigInteger successToDoCount) {
        this.member = member;
        this.categoryCode = categoryCode;
        this.countByToDo = countByToDo;
        this.successToDoCount = successToDoCount;
    }

    public void plusCountByToDo(){
        this.countByToDo.add(ONE);
    }

    public void minusCountByToDo(){
        this.countByToDo.subtract(ONE);
    }

    public void completeToDo(){
        this.successToDoCount.add(ONE);
    }

    public void cancelCompleteToDo(){
        this.successToDoCount.subtract(ONE);
    }
    public String getCodeName() {
        return categoryCode.getTitle();
    }


}
