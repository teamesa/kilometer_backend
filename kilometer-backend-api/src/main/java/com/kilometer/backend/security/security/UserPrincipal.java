package com.kilometer.backend.security.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kilometer.domain.user.dto.UserResponse;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.assertj.core.util.Lists;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private UserResponse user;

    public static UserPrincipal create(UserResponse user) {
        return new UserPrincipal(user);
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Lists.list(new SimpleGrantedAuthority(user.getRole().getKey()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    public long getId() {
        return user.getId();
    }
}
