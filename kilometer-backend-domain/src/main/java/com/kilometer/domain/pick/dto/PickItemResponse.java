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
public class PickItemResponse implements ItemInfoExtraction {

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

    public static PickItemResponse makePickItemResponse(Pick pick) {
        return PickItemResponse.builder()
                .id(pick.getPickedItem().getId())
                .listImageUrl(pick.getPickedItem().getListImageUrl())
                .thumbnailImageUrl(pick.getPickedItem().getThumbnailImageUrl())
                .title(pick.getPickedItem().getTitle())
                .exhibitionType(pick.getPickedItem().getExhibitionType())
                .feeType(pick.getPickedItem().getFeeType())
                .startDate(pick.getPickedItem().getStartDate())
                .endDate(pick.getPickedItem().getEndDate())
                .isHearted(pick.isHearted())
                .apiType(API_TYPE)
                .build();
    }

    public static PickItemResponse makePickItemResponse(ItemResponse itemResponse) {
        return PickItemResponse.builder()
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
