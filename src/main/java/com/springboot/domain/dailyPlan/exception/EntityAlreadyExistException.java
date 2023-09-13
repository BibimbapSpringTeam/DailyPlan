package com.springboot.domain.dailyPlan.exception;

import com.springboot.global.error.exception.BusinessException;
import com.springboot.global.error.exception.ErrorCode;

public class EntityAlreadyExistException extends BusinessException {

    public EntityAlreadyExistException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}