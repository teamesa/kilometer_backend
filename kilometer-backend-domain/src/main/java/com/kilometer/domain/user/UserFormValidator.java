package com.kilometer.domain.user;


import com.kilometer.domain.exception.UserValidationException;
import com.kilometer.domain.user.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
/*
 * this class will be used in User Package.
 * don't move this to other domain or don't add public access modifier.
 */
class UserFormValidator {
    private final UserRepository userRepository;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'+/=?{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String MIN_DATE_STRING = "1900-01-01";

    /**
     * this method will validate user's request.
     *
     * @throws UserValidationException this is exception for wrong user's request.
     */
    public void validateUserForm(UserUpdateRequest userUpdateRequest) {
        List<UserValidationException> exceptions = new ArrayList<>();

        validationEmail(userUpdateRequest, exceptions);
        validateGender(userUpdateRequest, exceptions);
        validatePhoneNumber(userUpdateRequest, exceptions);
        validateName(userUpdateRequest, exceptions);
        validateBirthday(userUpdateRequest, exceptions);

        if (!exceptions.isEmpty()) {
            throw new UserValidationException(exceptions, userUpdateRequest.getId());
        }
    }

    private void validateBirthday(UserUpdateRequest userUpdateRequest, List<UserValidationException> exceptions) {
        if (Objects.isNull(userUpdateRequest.getBirthDay())) {
            return;
        }

        if (userUpdateRequest.getBirthDay().isAfter(LocalDate.now())) {
            exceptions.add(new UserValidationException("생일을 올바르게 입력해주세요.", UserForm.birthDay));
        }

        if (userUpdateRequest.getBirthDay().isBefore(LocalDate.parse(MIN_DATE_STRING, DateTimeFormatter.ISO_DATE))) {
            exceptions.add(new UserValidationException("생일을 올바르게 입력해주세요.", UserForm.birthDay));
        }
    }

    private void validateName(UserUpdateRequest userUpdateRequest, List<UserValidationException> exceptions) {
        if (Objects.nonNull(userRepository.findByNameAndIdNot(userUpdateRequest.getName(), userUpdateRequest.getId()))) {
            exceptions.add(new UserValidationException("사용중인 닉네임입니다.", UserForm.name));
        }

        if (!StringUtils.hasText(userUpdateRequest.getName())) {
            exceptions.add(new UserValidationException("닉네임을 올바르게 입력해주세요.", UserForm.name));
        }

        if (userUpdateRequest.getName().length() > 20) {
            exceptions.add(new UserValidationException("닉네임은 20자 이상이 될 수 없습니다.", UserForm.name));
        }
    }

    private void validatePhoneNumber(UserUpdateRequest userUpdateRequest, List<UserValidationException> exceptions) {
        int phoneNumberLength = userUpdateRequest.getPhoneNumber().length();

        if (userUpdateRequest.getPhoneNumber().indexOf("01") != 0) {
            exceptions.add(new UserValidationException("핸드폰 번호를 올바르게 입력해주세요", UserForm.phoneNumber));
        }

        if (phoneNumberLength != 11 && phoneNumberLength != 10) {
            exceptions.add(new UserValidationException("핸드폰 번호를 올바르게 입력해주세요", UserForm.phoneNumber));
        }
    }

    private void validateGender(UserUpdateRequest userUpdateRequest, List<UserValidationException> exceptions) {
        if (!StringUtils.hasText(userUpdateRequest.getGender())) {
            exceptions.add(new UserValidationException("성별을 올바르게 선택해주세요", UserForm.gender));
        }
    }

    private void validationEmail(UserUpdateRequest userUpdateRequest, List<UserValidationException> exceptions) {
        if (!StringUtils.hasText(userUpdateRequest.getEmail())) {
            exceptions.add(new UserValidationException("이메일을 올바르게 입력해주세요.", UserForm.email));
        }

        if (!Pattern.compile(EMAIL_REGEX).matcher(userUpdateRequest.getEmail()).find()) {
            exceptions.add(new UserValidationException("이메일을 올바르게 입력해주세요.", UserForm.email));
        }
    }
}
