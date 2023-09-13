package com.springboot.global.firebase.exception;

import com.springboot.global.error.exception.BusinessException;
import com.springboot.global.error.exception.ErrorCode;

public class FirebaseTokenNotFoundException extends BusinessException {

    public FirebaseTokenNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
