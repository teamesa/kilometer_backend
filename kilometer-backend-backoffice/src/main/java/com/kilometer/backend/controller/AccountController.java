package com.kilometer.backend.controller;

import com.kilometer.backend.security.AccountPasswordEncoder;
import com.kilometer.domain.account.Account;
import com.kilometer.domain.account.AccountRequest;
import com.kilometer.domain.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountPasswordEncoder accountPasswordEncoder;

    @GetMapping("/account/{role}/{username}/{password}")
    public Account createAccount(@ModelAttribute AccountRequest account) {
        accountPasswordEncoder.encodePassword(account);
        return accountService.save(account);
    }

}