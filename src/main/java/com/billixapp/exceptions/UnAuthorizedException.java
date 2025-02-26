package com.billixapp.exceptions;

import com.billixapp.common.enums.ResponseCode;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException() {
        super(ResponseCode.UNAUTHORIZED.getDefaultMessage());
    }

    public UnAuthorizedException(String message) {
        super(message);
    }
}
