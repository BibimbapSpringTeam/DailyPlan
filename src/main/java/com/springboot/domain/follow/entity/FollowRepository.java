package com.springboot.domain.follow.entity;

import com.springboot.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Member> {
    boolean existsFollowByMemberAndFollowMember(Member member, Member followmember);
    List<Follow> findByMember(Member member);
    Follow findByMemberAndFollowMember(Member member, Member followmember);
}
