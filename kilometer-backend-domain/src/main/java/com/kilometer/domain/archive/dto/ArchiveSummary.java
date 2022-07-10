package com.kilometer.domain.archive.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "itemId")
@SuppressWarnings("unused")
public class ArchiveSummary {
    private long itemId;
    private double avgStarRating;
    private long archiveCount;
}
