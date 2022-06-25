package com.kilometer.backend.controller;

import com.kilometer.backend.controller.dto.UserFormErrorResponse;
import com.kilometer.domain.exception.UserValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class UserValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<UserFormErrorResponse> handleDataException(UserValidationException e) {
        UserFormErrorResponse response = UserFormErrorResponse.makeResponse(e);
        log.info("user make wrong request, please check user's response: {}", response);
        return ResponseEntity.badRequest().body(UserFormErrorResponse.makeResponse(e));
    }
}