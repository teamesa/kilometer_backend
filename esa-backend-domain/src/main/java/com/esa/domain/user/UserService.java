package com.esa.domain.user;

import com.esa.domain.user.dto.OAuth2UserInfo;
import com.esa.domain.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final NameGenerator nameGenerator;

    public Optional<UserResponse> findByEmail(String email) {
        Preconditions.notBlank(email, "eamil cannot be blank");
        return userRepository.findByEmail(email).map(User::toResponse);
    }

    public Optional<UserResponse> findById(Long id) {
        Preconditions.notNull(id, "id cannot be blank");
        return userRepository.findById(id).map(User::toResponse);
    }



    // DB에 존재하지 않을 경우 새로 등록
    public UserResponse registerNewUser(AuthProvider provider, OAuth2UserInfo oAuth2UserInfo) {
        User newUser = User.builder()
                .name(nameGenerator.makeRandomName())
                .email(oAuth2UserInfo.getEmail())
                .imageUrl(oAuth2UserInfo.getImageUrl())
                .gender(oAuth2UserInfo.getGender())
                .phoneNumber(oAuth2UserInfo.getPhoneNumber())
                .birthdate(oAuth2UserInfo.getBirthDate())
                .role(Role.USER)
                .provider(provider)
                .providerId(oAuth2UserInfo.getId())
                .build();
        return userRepository.save(newUser).toResponse();
    }
}
