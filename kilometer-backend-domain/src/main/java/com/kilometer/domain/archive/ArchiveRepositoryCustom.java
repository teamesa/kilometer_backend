package com.kilometer.domain.archive;

import com.kilometer.domain.archive.dto.ArchiveDetailDto;
import com.kilometer.domain.archive.dto.ArchiveQueryRequest;
import com.kilometer.domain.archive.dto.ItemArchiveDto;
import com.kilometer.domain.archive.dto.MyArchiveDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArchiveRepositoryCustom {

    Page<ItemArchiveDto> findAllByItemIdAndUserId(Pageable pageable,
        ArchiveQueryRequest queryRequest);

    Page<MyArchiveDto> findAllByUserId(Pageable pageable, ArchiveQueryRequest queryRequest);

    List<ArchiveDetailDto> findAllByArchiveIdAndUserId(long archiveId, long userId);

    Double avgStarRatingByItemId(long itemId);
}
