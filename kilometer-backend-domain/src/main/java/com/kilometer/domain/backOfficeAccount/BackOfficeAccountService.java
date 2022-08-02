package com.kilometer.domain.backOfficeAccount;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackOfficeAccountService {

    private final BackOfficeAccountRepository accountRepository;

    public BackOfficeAccount save(BackOfficeAccountRequest accountRequest) {
        return accountRepository.save(accountRequest.makeAccount());
    }

    public BackOfficeAccount findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
}
