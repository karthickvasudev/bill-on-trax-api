package com.billontrax.common.dtos;

import com.billontrax.common.enums.ResponseCode;
import lombok.Data;

@Data
public class ResponseStatus {
    private Integer code;
    private String message;

    public static ResponseStatus of(ResponseCode code) {
        ResponseStatus status = new ResponseStatus();
        status.code = code.getCode();
        status.message = code.getDefaultMessage();
        return status;
    }

    public static ResponseStatus of(ResponseCode code, String message) {
        ResponseStatus status = new ResponseStatus();
        status.code = code.getCode();
        status.message = message;
        return status;
    }
}
