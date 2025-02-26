package com.billixapp.exceptions;

import com.billixapp.common.enums.ResponseCode;

public class ErrorMessageException extends RuntimeException {
    public ErrorMessageException() {
        super(ResponseCode.ERROR.getDefaultMessage());
    }

    public ErrorMessageException(String message) {
        super(message);
    }
}
