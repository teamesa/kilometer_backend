package com.esa.backend.security.security;

import com.esa.backend.security.exception.ResourceNotFoundException;
import com.esa.domain.user.dto.UserResponse;
import com.esa.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        UserResponse user = userService.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(email + "로 된 사용자를 찾을 수 없습니다.")
                );

        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(Long id) {
        UserResponse user = userService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }
}