package com.kilometer.domain.homeModules.modules.monthlyFreeTicket.dto;

import com.kilometer.domain.converter.listItem.ItemInfoExtraction;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.FeeType;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MonthlyFreeTicketDto implements ItemInfoExtraction {

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
    @Builder.Default
    private String apiType = API_TYPE;

    @Override
    public String getPresentationImage() {
        return this.listImageUrl;
    }

    public static MonthlyFreeTicketDto from(MonthlyFreeTicket monthlyFreeTicket) {
        Boolean isHearted = monthlyFreeTicket.getIsHearted();
        Double avgStarRating = monthlyFreeTicket.getGrade();
        return MonthlyFreeTicketDto.builder()
            .id(monthlyFreeTicket.getId())
            .listImageUrl(monthlyFreeTicket.getListImageUrl())
            .title(monthlyFreeTicket.getTitle())
            .exhibitionType(monthlyFreeTicket.getExhibitionType())
            .feeType(monthlyFreeTicket.getFeeType())
            .startDate(monthlyFreeTicket.getStartDate())
            .endDate(monthlyFreeTicket.getEndDate())
            .isHearted(isHearted != null && isHearted)
            .pickCount(monthlyFreeTicket.getPickCount())
            .archiveCount(monthlyFreeTicket.getArchiveCount())
            .avgStarRating(avgStarRating == null ? 0.0 : avgStarRating)
            .build();
    }
}
