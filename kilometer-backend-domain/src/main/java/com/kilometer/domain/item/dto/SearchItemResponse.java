package com.kilometer.domain.item.dto;

import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.archive.dto.ArchiveSummary;
import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchItemResponse {

    private Long id;
    private String listImageUrl;
    private String title;
    private ExhibitionType exhibitionType;
    private FeeType feeType;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isHearted;
    private int pickCount;
    private int archiveCount;
    private float avgStarRating;

    public SearchItemResponse update(ArchiveSummary archiveSummary) {
        if (archiveSummary == null) {
            return this;
        }
        this.archiveCount = (int) archiveSummary.getArchiveCount();
        this.avgStarRating = (float) archiveSummary.getAvgStarRating();
        return this;
    }
}
