package com.kilometer.domain.item.dto;

import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.FeeType;
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
    private long archiveCount;
    private double avgStarRating;
}
