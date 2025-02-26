package com.billixapp.config;

import com.billixapp.common.dtos.ApiResponse;
import com.billixapp.common.dtos.ResponseStatus;
import com.billixapp.common.enums.ResponseCode;
import com.billixapp.exceptions.ErrorMessageException;
import com.billixapp.exceptions.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ControlAdviceConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ErrorMessageException.class)
    protected ResponseEntity<ApiResponse<Void>> handleErrorResponseException(ErrorMessageException e) {
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(new ResponseStatus(ResponseCode.ERROR, e.getMessage()));
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UnAuthorizedException.class)
    protected ResponseEntity<ApiResponse<Void>> handleUnAuthorizedException(UnAuthorizedException e) {
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(new ResponseStatus(ResponseCode.UNAUTHORIZED, e.getMessage()));
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ApiResponse<Void>> handleUndefinedException(Exception e) {
        log.error("Error handleUndefinedException:: ", e);
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(new ResponseStatus(ResponseCode.SERVER_ERROR));
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
