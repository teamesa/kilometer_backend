package com.kilometer.domain.authentication;

import com.kilometer.domain.authentication.dto.AuthRequest;
import com.kilometer.domain.authentication.dto.AuthResponse;
import com.kilometer.domain.authentication.dto.AuthUserDto;
import com.kilometer.domain.authentication.token.TokenProvider;
import com.kilometer.domain.user.NameGenerator;
import com.kilometer.domain.user.Role;
import com.kilometer.domain.user.User;
import com.kilometer.domain.user.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final NameGenerator nameGenerator;

    public AuthResponse authenticate(AuthRequest authRequest) {
        AuthUserDto authUserDto = AuthUserDto.from(authRequest);

        Optional<User> user = userRepository.findByProviderAndProviderId(authUserDto.getProvider(),
                authUserDto.getProviderId());
        if (user.isPresent()) {
            return new AuthResponse(tokenProvider.createToken(user.get().getId()), false);
        }
        User newUser = userRepository.save(creatNewUser(authUserDto));
        return new AuthResponse(tokenProvider.createToken(newUser.getId()), true);
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
