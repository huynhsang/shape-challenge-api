package com.sanght.shapechallenge.common.exception;

import com.sanght.shapechallenge.common.constant.ErrorCode;

public class ValidationException extends Exception {
    private int code = ErrorCode.VALIDATION_ERROR;
    public ValidationException(String msg){
        super(msg);
    }
}