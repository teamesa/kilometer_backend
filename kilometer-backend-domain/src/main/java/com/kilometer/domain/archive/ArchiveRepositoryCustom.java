package com.kilometer.domain.archive;

import com.kilometer.domain.archive.dto.ArchiveDetailDto;
import com.kilometer.domain.archive.dto.ArchiveQueryRequest;
import com.kilometer.domain.archive.dto.ItemArchiveDto;
import com.kilometer.domain.archive.dto.MyArchiveDto;
import com.kilometer.domain.archive.dto.RealTimeArchiveDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArchiveRepositoryCustom {

    Page<ItemArchiveDto> findAllItemArchiveByArchiveQueryRequest(Pageable pageable,
        ArchiveQueryRequest queryRequest);

    Page<MyArchiveDto> findAllMyArchiveByArchiveQueryRequest(Pageable pageable, ArchiveQueryRequest queryRequest);

    Optional<ArchiveDetailDto> findByArchiveIdAndUserIdAndIsVisible(long archiveId, long userId, boolean isVisible);

    Double avgStarRatingByItemId(long itemId);

    List<RealTimeArchiveDto> findRealTimeArchive(long archiveId);

    List<ArchiveEntity> findTopFourArchivesWithImageUrl();
}
