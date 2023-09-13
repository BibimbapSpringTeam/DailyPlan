package com.springboot.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ErrorCode Convention
 * - 도메인 별로 나누어 관리
 * - [주체_이유] 형태로 생성
 * - 코드는 도메인명 앞에서부터 1~2글자로 사용
 * - 메시지는 "~~다."로 마무리
 */

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Member
    MEMBER_NOT_FOUND(400, "M001", "존재 하지 않는 유저입니다."),
    USERNAME_ALREADY_EXIST(400, "M002", "이미 존재하는 사용자 이름입니다."),
    ACCOUNT_MISMATCH(401, "M005", "계정 정보가 일치하지 않습니다."),

    //DailyPlan
    DAILYPLAN_ALREADY_EXIST(400, "D001", "이미 데일리 플랜을 작성하였습니다. PUT으로 update 해주세요"),
    DAILYPLAN_NOT_FOUND(400, "D002", "존재 하지 않는 데일리 플랜입니다"),
    DAILYPLAN_MONTHLIST_NOT_FOUND(400, "D003", "해당 월에 작성된 데일리 플랜이 없습니다"),
    DAILYPLAN_NOT_HAVE_TODOLIST(400, "D004", "해당 데일리 플랜에 작성된 투두리스트가 없습니다"),

    //ToDoList
    TODOLIST_NOT_FOUND(400, "T001", "존재 하지 않는 투두리스트입니다"),

    //Category
    CATEGORYCODE_NOT_FOUND(400, "C001", "존재 하지 않는 카테고리입니다."),
    CATEGORYLIST_NOT_FOUND(400, "C002", "카테고리 리스트가 존재하지 않습니다."),

    //Firebase
    TOKEN_NOT_FOUND(400, "F001", "존재 하지 않는 토큰입니다"),
    PUSH_NOTIFICATION_FAIL(400, "F002", "메세지 발송에 실패했습니다."),

    //Follow
    FOLLOW_ALREADY_EXIST(400, "FL001", "이미 팔로우 하셨습니다."),
    FOLLOW_LIST_NOT_FOUND(400, "FL002", "팔로우 리스트가 존재하지 않습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}