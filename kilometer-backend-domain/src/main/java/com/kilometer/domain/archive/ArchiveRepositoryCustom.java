package com.kilometer.domain.archive;

import com.kilometer.domain.archive.dto.ItemArchiveDto;
import com.kilometer.domain.archive.dto.ArchiveSortType;
import com.kilometer.domain.archive.dto.MyArchiveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArchiveRepositoryCustom {

    Page<ItemArchiveDto> findAllByItemId(Pageable pageable, ArchiveSortType sortType,
        long itemId);
    Page<MyArchiveDto> findAllByUserId(Pageable pageable, ArchiveSortType sortType,
        long userId);

    Double avgStarRatingByItemId(long itemId);
}
