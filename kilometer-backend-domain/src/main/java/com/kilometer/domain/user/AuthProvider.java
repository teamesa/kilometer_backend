package com.kilometer.domain.user;

import java.util.stream.Stream;

public enum AuthProvider {
    naver("naver")
    ;

    private String value;

    AuthProvider(String value) {
        this.value = value;
    }

    public static AuthProvider from(String value) {
        return Stream.of(values())
                .filter(authProvider -> authProvider.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("인증 제공자가 올바르지 않습니다."));
    }
}
