package com.kilometer.domain.item.dto;

import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.FeeType;
import java.time.LocalDate;

import com.kilometer.domain.search.ItemInfoExtraction;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchItemResponse implements ItemInfoExtraction {

    private static final String API_TYPE = "search";

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
    private String apiType = API_TYPE;
}
