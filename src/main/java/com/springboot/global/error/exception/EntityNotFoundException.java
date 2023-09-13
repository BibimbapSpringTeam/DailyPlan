package com.springboot.global.error.exception;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(ErrorCode errorCode,String message) {
        super(errorCode, message);
    }
}