package com.kilometer.domain.archive;

import com.kilometer.domain.archive.dto.ArchiveQueryRequest;
import com.kilometer.domain.archive.dto.ArchiveSummary;
import com.kilometer.domain.archive.dto.ItemArchiveDto;
import com.kilometer.domain.archive.dto.MyArchiveDto;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArchiveRepositoryCustom {

    Page<ItemArchiveDto> findAllByItemIdAndUserId(Pageable pageable, ArchiveQueryRequest queryRequest);

    Page<MyArchiveDto> findAllByUserId(Pageable pageable, ArchiveQueryRequest queryRequest);

    Double avgStarRatingByItemId(long itemId);

    Map<Long, ArchiveSummary> findAllArchiveInfosByItemIds(List<Long> itemIds);
}
