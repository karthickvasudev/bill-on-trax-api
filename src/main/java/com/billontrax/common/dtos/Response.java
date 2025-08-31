package com.billontrax.common.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class Response<T> {
    private ResponseStatus status;
    private T data;

    public Response(ResponseStatus status) {
        this.status = status;
    }
}
