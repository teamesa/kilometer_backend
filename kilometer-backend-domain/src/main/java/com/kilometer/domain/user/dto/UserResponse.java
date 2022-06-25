package com.kilometer.domain.user.dto;

import com.kilometer.domain.user.AuthProvider;
import com.kilometer.domain.user.Gender;
import com.kilometer.domain.user.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String imageUrl;
    private Role role;
    private String phoneNumber;
    private LocalDateTime birthdate;
    private Gender gender;
    private AuthProvider provider;
    private boolean isCreated;
}
