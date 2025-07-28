package com.billontrax.common.config;

import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.common.exceptions.ErrorMessageException;
import com.billontrax.common.exceptions.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlerConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ErrorMessageException.class)
    protected ResponseEntity<Response<Void>> handleErrorResponseException(ErrorMessageException e) {
        Response<Void> response = new Response<>();
        response.setStatus(new ResponseStatus(ResponseCode.ERROR, e.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UnAuthorizedException.class)
    protected ResponseEntity<Response<Void>> handleUnAuthorizedException(UnAuthorizedException e) {
        Response<Void> response = new Response<>();
        response.setStatus(new ResponseStatus(ResponseCode.UNAUTHORIZED, e.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Response<Void>> handleUndefinedException(Exception e) {
        log.error("Error handleUndefinedException:: ", e);
        Response<Void> response = new Response<>();
        response.setStatus(new ResponseStatus(ResponseCode.SERVER_ERROR));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
