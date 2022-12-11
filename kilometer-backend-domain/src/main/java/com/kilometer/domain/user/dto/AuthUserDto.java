package com.kilometer.domain.user.dto;

import com.kilometer.domain.user.AuthProvider;
import com.kilometer.domain.user.Gender;
import com.kilometer.domain.util.BirthdateParser;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AuthUserDto {

    private String providerId;
    private AuthProvider provider;
    private String email;
    private String profileImage;
    private Gender gender;
    private LocalDateTime birthdate;
    private String phoneNumber;

    public static AuthUserDto from(AuthRequest authRequest) {
        return new AuthUserDto(authRequest.getProviderId(),
                AuthProvider.from(authRequest.getProvider()),
                authRequest.getEmail(),
                authRequest.getProfileImage(),
                Gender.from(authRequest.getGender()),
                BirthdateParser.generateBirthDate(authRequest.getBirthYear(), authRequest.getBirthday()),
                authRequest.getPhoneNumber());
    }
}
