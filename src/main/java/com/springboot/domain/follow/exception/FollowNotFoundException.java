package com.springboot.domain.follow.exception;

import com.springboot.global.error.exception.BusinessException;
import com.springboot.global.error.exception.ErrorCode;

public class FollowNotFoundException extends BusinessException {

    public FollowNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}