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
import static java.math.BigInteger.ZERO;

@Getter
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
    private int countByToDo;

    @Column
    private int successToDoCount;

    @Builder
    public Category(Member member, CategoryCode categoryCode, int countByToDo, int successToDoCount) {
        this.member = member;
        this.categoryCode = categoryCode;
        this.countByToDo = countByToDo;
        this.successToDoCount = successToDoCount;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void plusCountByToDo(){
        this.countByToDo += 1;
    }

    public void minusCountByToDo(){
        this.countByToDo -= 1;
    }

    public void completeToDo(){
        this.successToDoCount += 1;
    }

    public void cancelCompleteToDo(){
        this.successToDoCount -= 1;
    }
    public String getCodeName() {
        return categoryCode.getTitle();
    }


}
