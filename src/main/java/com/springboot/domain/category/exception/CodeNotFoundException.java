package com.springboot.domain.category.exception;

import com.springboot.global.error.exception.BusinessException;
import com.springboot.global.error.exception.ErrorCode;

public class CodeNotFoundException extends BusinessException {

    public  CodeNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
