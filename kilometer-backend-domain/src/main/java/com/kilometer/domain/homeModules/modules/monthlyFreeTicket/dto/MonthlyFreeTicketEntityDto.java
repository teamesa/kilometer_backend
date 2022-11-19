package com.kilometer.domain.homeModules.modules.monthlyFreeTicket.dto;

import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.converter.listItem.ItemInfoExtraction;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MonthlyFreeTicketEntityDto implements ItemInfoExtraction {
    private static final String API_TYPE = "monthlyFreeTicket";

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
