package com.kilometer.backend.security.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtils {
    private static final long ANONYMOUS_USER = -1L;

    /**
     * @return login user id.
     * also it can return -1, it is anonymous user(not login user).
     */
    public static long getLoginUserId() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(it -> it instanceof UserPrincipal)
                .map(it -> (UserPrincipal)it)
                .map(UserPrincipal::getId)
                .orElse(ANONYMOUS_USER);
    }
}
