package com.kilometer.domain.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
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
