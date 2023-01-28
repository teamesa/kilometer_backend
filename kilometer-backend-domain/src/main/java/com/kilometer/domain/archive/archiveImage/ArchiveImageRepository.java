package com.kilometer.domain.archive.archiveImage;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveImageRepository extends JpaRepository<ArchiveImageEntity, Long> {

    List<ArchiveImageEntity> findAllByArchiveEntityId(Long archiveEntityId);

    boolean existsArchiveImagesByArchiveEntityId(Long archiveEntityId);

    void deleteAllByArchiveEntityId(Long archiveEntityId);
}
