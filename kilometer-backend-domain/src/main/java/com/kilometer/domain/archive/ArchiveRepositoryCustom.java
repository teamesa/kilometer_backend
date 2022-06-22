package com.kilometer.domain.archive;

import com.kilometer.domain.archive.dto.ArchiveFetchUser;
import com.kilometer.domain.archive.dto.ArchiveSortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArchiveRepositoryCustom {

    Page<ArchiveFetchUser> findAllByItemId(Pageable pageable, ArchiveSortType sortType,
        long itemId);

    Double avgStarRatingByItemId(long itemId);
}
