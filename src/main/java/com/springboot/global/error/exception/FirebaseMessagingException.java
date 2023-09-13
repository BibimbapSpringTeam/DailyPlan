package com.springboot.global.error.exception;

import com.springboot.global.firebase.exception.FirebaseTokenNotFoundException;
import lombok.Getter;

@Getter
public class FirebaseMessagingException extends RuntimeException {

    private ErrorCode errorCode;

    public FirebaseMessagingException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
