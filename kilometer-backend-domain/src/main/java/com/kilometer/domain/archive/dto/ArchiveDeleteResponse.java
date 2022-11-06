package com.kilometer.domain.archive.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ArchiveDeleteResponse {
    private Long archiveId;

    public static ArchiveDeleteResponse from(Long archiveId) {
        return new ArchiveDeleteResponse(archiveId);
    }
}
