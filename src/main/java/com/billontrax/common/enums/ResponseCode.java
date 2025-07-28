package com.billontrax.common.enums;

import lombok.Getter;

@Getter
public enum ResponseCode {
    OK(200, "Your request has been processed successfully."),
    OK_NOTIFY(2000, "Your request has been processed successfully."),
    CREATED(201, "Your request was successful, and the resource has been created successfully."),
    UNAUTHORIZED(401, "You are not authorized to perform this action. Please log in and try again."),
    ERROR(400, "There was an issue with your request. Please check and try again."),
    SERVER_ERROR(500, "Oops! Something went wrong on our end. Please try again later or contact support.");

    private final Integer code;
    private final String defaultMessage;

    ResponseCode(Integer code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
}
