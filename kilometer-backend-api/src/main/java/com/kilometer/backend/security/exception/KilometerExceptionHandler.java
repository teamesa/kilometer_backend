package com.kilometer.backend.security.exception;

import com.kilometer.exception.KilometerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class KilometerExceptionHandler extends ResponseEntityExceptionHandler {

    private final ErrorStatusMapper mapper;

    public KilometerExceptionHandler(ErrorStatusMapper mapper) {
        this.mapper = mapper;
    }

    @ExceptionHandler(KilometerException.class)
    public ResponseEntity<ErrorResponse> errorResponse(KilometerException exception) {
        HttpStatus status = mapper.getStatus(exception.getErrorCode());
        ErrorResponse response = ErrorResponse.from(exception.getErrorCode());
        return new ResponseEntity<>(response, status);
    }
}
