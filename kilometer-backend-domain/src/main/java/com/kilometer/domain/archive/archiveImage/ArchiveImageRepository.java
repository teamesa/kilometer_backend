package com.kilometer.domain.archive.archiveImage;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveImageRepository extends JpaRepository<ArchiveImage, Long> {

    List<ArchiveImage> findAllByArchiveId(Long archiveId);
}
