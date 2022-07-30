package com.kilometer.domain.backOfficeAccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BackOfficeAccountRequest {

    String username;
    String password;
    String role;

    public BackOfficeAccount makeAccount() {
        return BackOfficeAccount.builder()
                .username(this.username)
                .password(this.password)
                .role(this.role)
                .build();
    }

    public void encodePassword (String password) {
        this.password = password;
    }
}
