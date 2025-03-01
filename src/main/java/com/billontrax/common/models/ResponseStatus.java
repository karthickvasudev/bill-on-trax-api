package com.billontrax.common.models;

import com.billontrax.common.enums.ResponseCode;
import lombok.Data;


@Data
public class ResponseStatus {
    private final Integer code;
    private final String message;

    public ResponseStatus(ResponseCode code) {
        this.code = code.getCode();
        this.message = code.getDefaultMessage();
    }

    public ResponseStatus(ResponseCode code, String message) {
        this.code = code.getCode();
        this.message = message;
    }
}
