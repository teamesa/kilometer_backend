package com.kilometer.domain.archive.heart;

import com.kilometer.domain.archive.dto.ArchiveFetchUser;
import com.kilometer.domain.util.ApiUrlUtils;
import org.springframework.stereotype.Component;

@Component
public class ArchiveHeartGenerator {

    public ArchiveHeart generateArchiveHeart(ArchiveFetchUser archiveFetchUser) {
        return ArchiveHeart.builder()
            .heartClicked(archiveFetchUser.isHeart())
            .link(ApiUrlUtils.getLikeArchiveUrl(archiveFetchUser.getId()))
            .build();
    }

    public ArchiveHeart generateArchiveHeart(long archiveId) {
        return ArchiveHeart.builder()
            .heartClicked(false)
            .link(ApiUrlUtils.getLikeArchiveUrl(archiveId))
            .build();
    }

}
