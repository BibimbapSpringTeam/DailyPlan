package com.springboot.domain.toDo.exception;

import com.springboot.global.error.exception.BusinessException;
import com.springboot.global.error.exception.ErrorCode;

public class ToDoListNotFoundException extends BusinessException {

    public ToDoListNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
