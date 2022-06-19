package com.kilometer.domain.exception;

import com.kilometer.domain.user.UserForm;
import lombok.Getter;
import org.junit.platform.commons.PreconditionViolationException;

import java.util.List;

@Getter
/*
  this Exception controlled By @UserValidationExceptionHandler
  so please DO NOT Catch this Runtime Exception.
 */
public class UserValidationException extends RuntimeException {
    private final long userId;
    private final UserForm bindingForm;
    private final List<UserValidationException> userValidationExceptionList;

    public UserValidationException(PreconditionViolationException violationException, UserForm bindingForm) {
        super(violationException.getMessage(), violationException.getCause());
        userId = 0;
        this.bindingForm = bindingForm;
        this.userValidationExceptionList = List.of();
    }

    public UserValidationException(List<UserValidationException> userValidationExceptionList, long userId) {
        super("");
        this.bindingForm = null;
        this.userId = userId;
        this.userValidationExceptionList = userValidationExceptionList;
    }
}
