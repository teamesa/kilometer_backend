package com.kilometer.domain.authentication.token;

import com.kilometer.domain.user.User;

public interface TokenProvider {

    String createToken(User user);
    Long getUserIdFromToken(String token);
    boolean validateToken(String authToken);
}
