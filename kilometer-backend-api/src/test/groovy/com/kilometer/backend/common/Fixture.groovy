package com.kilometer.backend.common;

import com.kilometer.domain.user.AuthProvider;
import com.kilometer.domain.user.Gender;
import com.kilometer.domain.user.Role
import com.kilometer.domain.user.User
import com.kilometer.domain.user.dto.AuthRequest;
import com.kilometer.domain.user.dto.UserResponse
import org.springframework.security.core.AuthenticatedPrincipal
import spock.lang.Shared;

import java.time.LocalDateTime;

class Fixture {

        @Shared
        static final UserResponse USER_RESPONSE = UserResponse.builder()
                .id(1L)
                .name("user")
                .email("example@naver.com")
                .imageUrl("/img.png")
                .role(Role.USER)
                .phoneNumber("01012345678")
                .birthdate(LocalDateTime.MAX)
                .gender(Gender.MALE)
                .provider(AuthProvider.naver)
                .isCreated(true)
                .build()

        @Shared
        static final AuthRequest AUTH_REQUEST = AuthRequest.builder()
                .providerId("1")
                .provider("naver")
                .email("example@naver.com")
                .profileImage("/img.png")
                .gender("M")
                .birthday("03-28")
                .birthYear("1999")
                .phoneNumber("01012345678")
                .build()
}
