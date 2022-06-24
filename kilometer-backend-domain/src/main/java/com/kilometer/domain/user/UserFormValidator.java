package com.kilometer.domain.user;

import com.kilometer.domain.exception.UserValidationException;
import com.kilometer.domain.user.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.PreconditionViolationException;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
/*
 * this class will be used in User Package.
 * don't move this to other domain or don't add public access modifier.
 */
class UserFormValidator {
    private final UserRepository userRepository;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'\\*+/=?{|}~^.-]+@[a-zA-Z0-9.-]+$";

    /**
     * this method will validate user's request.
     * @throws UserValidationException this is exception for wrong user's request.
     */
    public void validateUserForm(UserUpdateRequest userUpdateRequest) {
        List<UserValidationException> exceptions = new ArrayList<>();

        validationEmail(userUpdateRequest, exceptions);
        validateGender(userUpdateRequest, exceptions);
        validatePhoneNumber(userUpdateRequest, exceptions);
        validateName(userUpdateRequest, exceptions);

        if (!exceptions.isEmpty()) {
            throw new UserValidationException(exceptions, userUpdateRequest.getId());
        }
    }

    private void validateName(UserUpdateRequest userUpdateRequest, List<UserValidationException> exceptions) {
        try {
            Preconditions.condition(
                    Objects.isNull(userRepository.findByNameAndIdNot(userUpdateRequest.getName(), userUpdateRequest.getId())),
                    "사용중인 닉네임입니다."
            );
        } catch (PreconditionViolationException e) {
            exceptions.add(new UserValidationException(e, UserForm.name));
        }

        try {
            Preconditions.notBlank(userUpdateRequest.getName(), "닉네임을 올바르게 입력해주세요");
        } catch (PreconditionViolationException e) {
            exceptions.add(new UserValidationException(e, UserForm.name));
        }
    }

    private void validatePhoneNumber(UserUpdateRequest userUpdateRequest, List<UserValidationException> exceptions) {
        int phoneNumberLength = userUpdateRequest.getPhoneNumber().length();
        try {
            Preconditions.condition(userUpdateRequest.getPhoneNumber().indexOf("01") == 0
                    , "핸드폰 번호를 올바르게 입력해주세요");
        } catch (PreconditionViolationException e) {
            exceptions.add(new UserValidationException(e, UserForm.phoneNumber));
        }

        try {
            Preconditions.condition(phoneNumberLength == 10 || phoneNumberLength == 11, "핸드폰 번호를 올바르게 입력해주세요.");
        } catch (PreconditionViolationException e) {
            exceptions.add(new UserValidationException(e, UserForm.phoneNumber));
        }
    }

    private void validateGender(UserUpdateRequest userUpdateRequest, List<UserValidationException> exceptions) {
        try {
            Preconditions.notBlank(userUpdateRequest.getGender(), "성별을 올바르게 선택해주세요");
        } catch (PreconditionViolationException e) {
            exceptions.add(new UserValidationException(e, UserForm.gender));
        }

        try {
            Preconditions.notEmpty(
                    Arrays.stream(Gender.values())
                            .filter(gender -> gender.name().equals(userUpdateRequest.getGender()))
                            .collect(Collectors.toList()),
                    "성별을 올바르게 선택해주세요"
            );
        } catch (PreconditionViolationException e) {
            exceptions.add(new UserValidationException(e, UserForm.gender));
        }
    }

    private void validationEmail(UserUpdateRequest userUpdateRequest, List<UserValidationException> exceptions) {
        try {
            Preconditions.notBlank(userUpdateRequest.getEmail(), "이메일을 올바르게 입력해주세요.");
        } catch (PreconditionViolationException e) {
            exceptions.add(new UserValidationException(e, UserForm.email));
        }

        try {
            Preconditions.condition(
                    Pattern.compile(EMAIL_REGEX).matcher(userUpdateRequest.getEmail()).find(),
                    "이메일을 올바르게 입력해주세요."
            );
        } catch (PreconditionViolationException e) {
            exceptions.add(new UserValidationException(e, UserForm.email));
        }
    }
}
