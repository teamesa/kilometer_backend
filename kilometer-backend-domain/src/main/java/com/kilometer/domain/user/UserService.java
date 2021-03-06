package com.kilometer.domain.user;

import com.kilometer.domain.image.ImageService;
import com.kilometer.domain.user.dto.OAuth2UserInfo;
import com.kilometer.domain.user.dto.UserProfileUpdate;
import com.kilometer.domain.user.dto.UserResponse;
import com.kilometer.domain.user.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String NAVER_DEFAULT_PROFILE_IMAGE = "https://ssl.pstatic.net/static/pwe/address/img_profile.png";

    private final UserFormValidator userFormValidator;
    private final UserRepository userRepository;
    private final NameGenerator nameGenerator;
    private final ImageService imageService;

    public Optional<UserResponse> findByEmail(String email) {
        Preconditions.notBlank(email, "eamil cannot be blank");
        return userRepository.findByEmail(email).map(User::toResponse);
    }

    public Optional<UserResponse> findById(Long id) {
        Preconditions.notNull(id, "id cannot be blank");
        return userRepository.findById(id).map(User::toResponse);
    }

    public Optional<UserResponse> updateUser(UserUpdateRequest userUpdateRequest) {
        userFormValidator.validateUserForm(userUpdateRequest);
        return userRepository.findById(userUpdateRequest.getId())
                .map(user -> user.update(userUpdateRequest))
                .map(userRepository::save)
                .map(User::toResponse);
    }

    // DB에 존재하지 않을 경우 새로 등록
    public UserResponse registerNewUser(AuthProvider provider, OAuth2UserInfo oAuth2UserInfo) {
        User newUser = User.builder()
                .name(nameGenerator.makeRandomName())
                .email(oAuth2UserInfo.getEmail())
                .imageUrl(profileImageToNull(oAuth2UserInfo.getImageUrl()))
                .gender(oAuth2UserInfo.getGender())
                .phoneNumber(oAuth2UserInfo.getPhoneNumber())
                .birthdate(oAuth2UserInfo.getBirthDate())
                .role(Role.USER)
                .provider(provider)
                .providerId(oAuth2UserInfo.getId())
                .build();
        return userRepository.save(newUser).toResponse(true);
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

    private String profileImageToNull(String profileImage) {
        if (StringUtils.hasText(profileImage) && NAVER_DEFAULT_PROFILE_IMAGE.equals(profileImage)) {
            return null;
        }
        return profileImage;
    }
}
