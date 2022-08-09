package com.kilometer.domain.backOfficeAccount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BackOfficeAccountRepository extends JpaRepository<BackOfficeAccount, Long> {
    BackOfficeAccount findByUsername(String username);
}
