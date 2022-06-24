package com.kilometer.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserUpdateRequest {
    private long id;
    private String name;
    private String email;
    private String gender;
    private String phoneNumber;
    private LocalDate birthDay;
}
