package com.billontrax.common.config;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.common.exceptions.ErrorMessageException;
import com.billontrax.common.exceptions.UnAuthorizedException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandlerConfig {

    @ExceptionHandler(value = ErrorMessageException.class)
    protected ResponseEntity<Response<Void>> handleErrorResponseException(ErrorMessageException e) {
        log.error("Error message:: {}", e.getMessage(), e);
        Response<Void> response = new Response<>(ResponseStatus.of(ResponseCode.ERROR, e.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UnAuthorizedException.class)
    protected ResponseEntity<Response<Void>> handleUnAuthorizedException(UnAuthorizedException e) {
        log.error("Unauthorized error:: {}", e.getMessage(), e);
        Response<Void> response = new Response<>(ResponseStatus.of(ResponseCode.UNAUTHORIZED, e.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()).toList();
        log.error("Validation error:: {}", ex.getMessage(), ex);
        Response<Void> response = new Response<>(ResponseStatus.of(ResponseCode.ERROR, String.join("\n", errors)));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Response<Void>> handleUndefinedException(Exception e) {
        log.error("Error handle UndefinedException:: {}", e.getMessage(), e);
        Response<Void> response = new Response<>(ResponseStatus.of(ResponseCode.SERVER_ERROR));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
