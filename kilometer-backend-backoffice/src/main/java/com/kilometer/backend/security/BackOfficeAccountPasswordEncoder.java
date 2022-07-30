package com.kilometer.backend.security;

import com.kilometer.domain.backOfficeAccount.BackOfficeAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BackOfficeAccountPasswordEncoder {

    private final PasswordEncoder passwordEncoder;

    public void encodePassword (BackOfficeAccountRequest account) {
        account.encodePassword(passwordEncoder.encode(account.getPassword()));
    }
}
