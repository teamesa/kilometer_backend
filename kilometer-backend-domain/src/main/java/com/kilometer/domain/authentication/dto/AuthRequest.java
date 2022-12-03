package com.kilometer.domain.authentication.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthRequest {

    private String providerId;
    private String provider;
    private String email;
    private String profileImage;
    private String gender;
    private String birthday;
    private String birthYear;
    private String phoneNumber;
}
