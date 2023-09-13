package com.springboot.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberFirebaseTokenRequestDto {

    private String firebaseToken;

    @Builder
    public MemberFirebaseTokenRequestDto(String FirebaseToken) {
        this.firebaseToken = firebaseToken;
    }
}
