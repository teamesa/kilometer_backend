package com.kilometer.backend.security;

import com.kilometer.domain.account.AccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountPasswordEncoder {

    private final PasswordEncoder passwordEncoder;

    public void encodePassword (AccountRequest account) {
        account.encodePassword(passwordEncoder.encode(account.getPassword()));
    }
}
