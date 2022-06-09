package com.kilometer.domain.archive;

import com.kilometer.domain.archive.dto.ArchiveSortType;
import com.kilometer.domain.archive.queryDto.ArchiveSelectResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArchiveRepositoryCustom {
    Page<ArchiveSelectResult> findAllByItemId(Pageable pageable, ArchiveSortType sortType, long itemId);
    Double avgStarRatingByItemId(long itemId);
}
