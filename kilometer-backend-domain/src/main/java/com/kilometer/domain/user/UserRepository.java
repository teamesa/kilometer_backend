package com.kilometer.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    User findByNameAndIdNot(String name, long id);
    Optional<User> findByProviderAndProviderId(AuthProvider provider, String providerId);
}
