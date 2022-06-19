package com.kilometer.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@NoArgsConstructor
public class UserUpdateRequest {
    private long id;
    private String name;
    private String email;
    private String gender;
    private String phoneNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDay;
}
