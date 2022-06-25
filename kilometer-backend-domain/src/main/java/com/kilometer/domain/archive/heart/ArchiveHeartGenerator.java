package com.kilometer.domain.archive.heart;

import com.kilometer.domain.archive.dto.ItemArchiveDto;
import com.kilometer.domain.util.ApiUrlUtils;
import org.springframework.stereotype.Component;

@Component
public class ArchiveHeartGenerator {

    public ArchiveHeart generateArchiveHeart(ItemArchiveDto itemArchiveDto) {
        return ArchiveHeart.builder()
            .heartClicked(itemArchiveDto.isHeart())
            .link(ApiUrlUtils.getLikeArchiveUrl(itemArchiveDto.getId()))
            .build();
    }

    public ArchiveHeart generateArchiveHeart(long archiveId) {
        return ArchiveHeart.builder()
            .heartClicked(false)
            .link(ApiUrlUtils.getLikeArchiveUrl(archiveId))
            .build();
    }

}
