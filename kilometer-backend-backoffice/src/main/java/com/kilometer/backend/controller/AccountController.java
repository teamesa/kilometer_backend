package com.kilometer.backend.controller;

import com.kilometer.backend.security.BackOfficeAccountPasswordEncoder;
import com.kilometer.domain.backOfficeAccount.BackOfficeAccount;
import com.kilometer.domain.backOfficeAccount.BackOfficeAccountRequest;
import com.kilometer.domain.backOfficeAccount.BackOfficeAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final BackOfficeAccountService accountService;
    private final BackOfficeAccountPasswordEncoder accountPasswordEncoder;

    @GetMapping("/account/{role}/{username}/{password}")
    public BackOfficeAccount createAccount(@ModelAttribute BackOfficeAccountRequest account) {
        accountPasswordEncoder.encodePassword(account);
        return accountService.save(account);
    }

}