package com.kilometer.backend.security.security.token;

import com.kilometer.domain.user.dto.UserResponse;

public interface TokenProvider {

    String createToken(UserResponse user);
    Long getUserIdFromToken(String token);
    boolean validateToken(String authToken);
}
