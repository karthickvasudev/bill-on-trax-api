package com.billixapp.common.dtos;

import com.billixapp.common.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
