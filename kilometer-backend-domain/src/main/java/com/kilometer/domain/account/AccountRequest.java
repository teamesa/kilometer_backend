package com.kilometer.domain.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    String username;
    String password;
    String role;

    public Account makeAccount() {
        return Account.builder()
                .username(this.username)
                .password(this.password)
                .role(this.role)
                .build();
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
