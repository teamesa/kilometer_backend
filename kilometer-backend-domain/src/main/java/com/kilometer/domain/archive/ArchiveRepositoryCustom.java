package com.kilometer.domain.archive;

import com.kilometer.domain.archive.dto.ItemArchiveDto;
import com.kilometer.domain.archive.dto.ArchiveSortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArchiveRepositoryCustom {

    Page<ItemArchiveDto> findAllByItemId(Pageable pageable, ArchiveSortType sortType,
        long itemId);

    Double avgStarRatingByItemId(long itemId);
}
