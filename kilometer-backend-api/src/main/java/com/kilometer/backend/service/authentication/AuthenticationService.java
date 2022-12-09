package com.kilometer.backend.service.authentication;

import com.kilometer.backend.security.security.token.TokenProvider;
import com.kilometer.domain.user.UserService;
import com.kilometer.domain.user.dto.AuthRequest;
import com.kilometer.domain.user.dto.AuthResponse;
import com.kilometer.domain.user.dto.UserResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final TokenProvider tokenProvider;
    private final UserService userService;

    public AuthResponse authenticate(AuthRequest authRequest) {
        Optional<UserResponse> user = userService.findUserByProviderAndProviderId(authRequest);
        if (user.isPresent()) {
            return new AuthResponse(tokenProvider.createToken(user.get()), false);
        }
        UserResponse newUser = userService.saveUser(authRequest);
        return new AuthResponse(tokenProvider.createToken(newUser), true);
    }
}
