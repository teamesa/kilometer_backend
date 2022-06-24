package com.kilometer.domain.archive;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveRepository extends JpaRepository<Archive, Long>, ArchiveRepositoryCustom {

    Optional<Archive> findByItemIdAndUserId(Long itemId, Long userId);

    boolean existsByItemIdAndUserId(Long itemId, Long userId);
}
