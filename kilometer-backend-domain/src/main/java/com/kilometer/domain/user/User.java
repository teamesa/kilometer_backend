package com.kilometer.domain.user;

import com.kilometer.domain.user.dto.UserResponse;
import com.kilometer.domain.user.dto.UserUpdateRequest;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String imageUrl;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private String phoneNumber;

    @Column
    private LocalDateTime birthdate;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column
    private String providerId;

    public UserResponse toResponse() {
        return UserResponse.builder()
                .name(this.name)
                .role(this.role)
                .imageUrl(this.imageUrl)
                .provider(this.provider)
                .email(this.email)
                .id(this.id)
                .birthdate(this.birthdate)
                .gender(this.gender)
                .phoneNumber(this.phoneNumber)
                .build();
    }

    public UserResponse toResponse(boolean isCreated) {
        return UserResponse.builder()
                .name(this.name)
                .role(this.role)
                .imageUrl(this.imageUrl)
                .provider(this.provider)
                .email(this.email)
                .id(this.id)
                .birthdate(this.birthdate)
                .gender(this.gender)
                .phoneNumber(this.phoneNumber)
                .isCreated(isCreated)
                .build();
    }

    public User update(UserUpdateRequest userUpdateRequest) {
        this.name = userUpdateRequest.getName();
        this.email = userUpdateRequest.getEmail();
        this.gender = Gender.valueOf(userUpdateRequest.getGender());
        this.phoneNumber = userUpdateRequest.getPhoneNumber();
        if (Objects.nonNull(userUpdateRequest.getBirthDay())) {
            this.birthdate = userUpdateRequest.getBirthDay().atStartOfDay();
        } else {
            this.birthdate = null;
        }
        return this;
    }

    public User updateProfile(String profileImage) {
        this.imageUrl = profileImage;
        return this;
    }
}
