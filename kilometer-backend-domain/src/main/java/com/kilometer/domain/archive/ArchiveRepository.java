package com.kilometer.domain.archive;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveRepository extends JpaRepository<Archive, Long>, ArchiveRepositoryCustom {

    List<Archive> findAllByItemIdAndUserId(Long itemId, Long userId);

    Optional<Archive> findByItemIdAndUserId(Long itemId, Long userId);
}
