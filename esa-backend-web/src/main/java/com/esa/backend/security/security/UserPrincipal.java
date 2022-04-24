package com.esa.backend.security.security;

import com.esa.domain.user.Role;
import com.esa.domain.user.dto.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.assertj.core.util.Lists;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@AllArgsConstructor
public class UserPrincipal implements OAuth2User, UserDetails {
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

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        return user.getName();
    }

    public long getId() {
        return user.getId();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getBody() {
        return (Map<String, Object>) OBJECT_MAPPER.convertValue(user, Map.class);
    }
}