package com.billontrax.common.exceptions;

import com.billontrax.common.enums.ResponseCode;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException() {
        super(ResponseCode.UNAUTHORIZED.getDefaultMessage());
    }

    public UnAuthorizedException(String message) {
        super(message);
    }
}
