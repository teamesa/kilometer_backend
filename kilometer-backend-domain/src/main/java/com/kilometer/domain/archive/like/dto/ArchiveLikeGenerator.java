package com.kilometer.domain.archive.like.dto;

import com.kilometer.domain.archive.dto.ItemArchiveDto;
import com.kilometer.domain.util.ApiUrlUtils;
import org.springframework.stereotype.Component;

@Component
public class ArchiveLikeGenerator {

    public ArchiveLike generateArchiveLike(ItemArchiveDto itemArchiveDto) {
        return ArchiveLike.builder()
            .heartClicked(itemArchiveDto.isLiked())
            .link(ApiUrlUtils.getLikeArchiveUrl(itemArchiveDto.getId()))
            .build();
    }

    public ArchiveLike generateArchiveLike(long archiveId) {
        return ArchiveLike.builder()
            .heartClicked(false)
            .link(ApiUrlUtils.getLikeArchiveUrl(archiveId))
            .build();
    }

}
