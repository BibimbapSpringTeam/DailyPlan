package com.springboot.domain.category.exception;

import com.springboot.global.error.exception.BusinessException;
import com.springboot.global.error.exception.ErrorCode;

public class CategoryListNotFoundException extends BusinessException {


    public CategoryListNotFoundException(ErrorCode errorCode, String message) {

        super(errorCode, message);
    }
}
