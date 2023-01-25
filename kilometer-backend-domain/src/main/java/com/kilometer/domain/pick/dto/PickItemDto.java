package com.kilometer.domain.pick.dto;

import com.kilometer.domain.item.dto.ItemResponse;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.pick.Pick;
import com.kilometer.domain.converter.listItem.ItemInfoExtraction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PickItemDto implements ItemInfoExtraction {

    private static final String API_TYPE = "pick";

    private Long id;
    private String listImageUrl;
    private String thumbnailImageUrl;
    private String title;
    private ExhibitionType exhibitionType;
    private FeeType feeType;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isHearted;
    private String apiType;

    public void updateApiTypeToPick() {
        this.apiType = API_TYPE;
    }

    public static PickItemDto makePickItemResponse(ItemResponse itemResponse) {
        return PickItemDto.builder()
                .id(itemResponse.getId())
                .listImageUrl(itemResponse.getListImageUrl())
                .thumbnailImageUrl(itemResponse.getThumbnailImageUrl())
                .title(itemResponse.getTitle())
                .exhibitionType(itemResponse.getExhibitionType())
                .feeType(itemResponse.getFeeType())
                .startDate(itemResponse.getStartDate())
                .endDate(itemResponse.getEndDate())
                .isHearted(true)
                .apiType(API_TYPE)
                .build();
    }

    @Override
    public String getPresentationImage() {
        return this.thumbnailImageUrl;
    }
}
