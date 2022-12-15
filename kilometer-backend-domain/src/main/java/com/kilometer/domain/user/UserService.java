package com.kilometer.domain.user;

import com.google.common.base.Preconditions;
import com.kilometer.domain.image.ImageService;
import com.kilometer.domain.user.dto.AuthRequest;
import com.kilometer.domain.user.dto.AuthUserDto;
import com.kilometer.domain.user.dto.UserProfileUpdate;
import com.kilometer.domain.user.dto.UserResponse;
import com.kilometer.domain.user.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String NAVER_DEFAULT_PROFILE_IMAGE = "https://ssl.pstatic.net/static/pwe/address/img_profile.png";

    private final NameGenerator nameGenerator;
    private final UserFormValidator userFormValidator;
    private final UserRepository userRepository;
    private final ImageService imageService;

    public Optional<UserResponse> findByEmail(String email) {
        Preconditions.checkArgument(StringUtils.hasText(email), "email cannot be blank");
        return userRepository.findByEmail(email).map(User::toResponse);
    }

    public Optional<UserResponse> findById(Long id) {
        Preconditions.checkNotNull(id, "id cannot be blank");
        return userRepository.findById(id).map(User::toResponse);
    }

    public Optional<UserResponse> updateUser(UserUpdateRequest userUpdateRequest) {
        userFormValidator.validateUserForm(userUpdateRequest);
        return userRepository.findById(userUpdateRequest.getId())
                .map(user -> user.update(userUpdateRequest))
                .map(userRepository::save)
                .map(User::toResponse);
    }

    public Optional<UserResponse> updateUserProfile(UserProfileUpdate profileUpdate) throws IOException {
        String profileUrl = imageService.upload(profileUpdate.getFile(),
                profileUpdate.getFileName(),
                profileUpdate.getFolderName(),
                profileUpdate.getMaxFileSize()
        );

        return userRepository.findById(profileUpdate.getUserId())
                .map(user -> user.updateProfile(profileUrl))
                .map(userRepository::save)
                .map(User::toResponse);
    }

    public Optional<UserResponse> findUserByProviderAndProviderId(AuthRequest authRequest) {
        AuthUserDto authUserDto = AuthUserDto.from(authRequest);
        return userRepository.findByProviderAndProviderId(authUserDto.getProvider(), authUserDto.getProviderId())
                .map(User::toResponse);
    }

    public UserResponse saveUser(AuthRequest authRequest) {
        AuthUserDto authUserDto = AuthUserDto.from(authRequest);
        return userRepository.save(creatNewUser(authUserDto))
                .toResponse();
    }

    private User creatNewUser(final AuthUserDto authUserDto) {
        return User.builder()
                .name(nameGenerator.makeRandomName())
                .email(authUserDto.getEmail())
                .imageUrl(authUserDto.getProfileImage())
                .role(Role.USER)
                .phoneNumber(authUserDto.getPhoneNumber())
                .birthdate(authUserDto.getBirthdate())
                .gender(authUserDto.getGender())
                .provider(authUserDto.getProvider())
                .providerId(authUserDto.getProviderId())
                .build();
    }
}
