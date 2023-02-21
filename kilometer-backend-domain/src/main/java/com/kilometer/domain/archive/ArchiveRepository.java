package com.kilometer.domain.archive;

import com.kilometer.domain.archive.exception.ArchiveNotFoundException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveRepository extends JpaRepository<ArchiveEntity, Long>, ArchiveRepositoryCustom {

    Optional<ArchiveEntity> findByItemIdAndUserId(Long itemId, Long userId);

    Optional<ArchiveEntity> findByIdAndUserId(final Long id, final Long userId);

    boolean existsByItemIdAndUserId(Long itemId, Long userId);

    default ArchiveEntity getByIdAndUserId(final Long id, final Long userId) {
        return this.findByIdAndUserId(id, userId)
            .orElseThrow(() -> new ArchiveNotFoundException("해당 회원에게 일치하는 아카이브가 없습니다."));
    }
}
