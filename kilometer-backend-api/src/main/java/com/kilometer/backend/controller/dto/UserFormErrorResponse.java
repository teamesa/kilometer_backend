package com.kilometer.backend.controller.dto;

import com.kilometer.domain.exception.UserValidationException;
import com.kilometer.domain.user.UserForm;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.stream.Collectors;

@Getter
@Builder
@ToString
public class UserFormErrorResponse {
    private long id;
    private String phoneNumberMessage;
    private String nameMessage;
    private String genderMessage;
    private String birthDayMessage;
    private String emailMessage;

    public static UserFormErrorResponse makeResponse(UserValidationException e) {
        return UserFormErrorResponse.builder()
                .phoneNumberMessage(getMessageByForm(UserForm.phoneNumber, e))
                .nameMessage(getMessageByForm(UserForm.name, e))
                .genderMessage(getMessageByForm(UserForm.gender, e))
                .birthDayMessage(getMessageByForm(UserForm.birthDay, e))
                .emailMessage(getMessageByForm(UserForm.email, e))
                .build();

    }

    private static String getMessageByForm(UserForm form, UserValidationException e) {
        return e.getUserValidationExceptionList().stream()
                .filter(exception -> exception.getBindingForm().equals(form))
                .map(Throwable::getMessage)
                .collect(Collectors.joining("\n"));
    }
}
