package com.springboot.domain.member.dto;

import com.springboot.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//Getter 없으면 dto데이터를 빼서 Entity생성 불가
@Getter
@NoArgsConstructor
public class MemberRequestDto {
    private String name;
    private String email;
    private String profileUrl;

    //@NoArgsConstructor로 기본생성자 생성, Builder 굳이 필요X
    @Builder
    public MemberRequestDto(String name, String email, String profileUrl) {
        this.name = name;
        this.email = email;
        this.profileUrl = profileUrl;
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .profileUrl(profileUrl)
                .build();
    }
}
