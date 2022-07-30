package com.kilometer.domain.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account save(AccountRequest accountRequest) {
        return accountRepository.save(accountRequest.makeAccount());
    }

    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
}
