package com.billontrax.common.exceptions;

import com.billontrax.common.enums.ResponseCode;

public class ErrorMessageException extends RuntimeException {
    public ErrorMessageException() {
        super(ResponseCode.ERROR.getDefaultMessage());
    }

    public ErrorMessageException(String message) {
        super(message);
    }
}
