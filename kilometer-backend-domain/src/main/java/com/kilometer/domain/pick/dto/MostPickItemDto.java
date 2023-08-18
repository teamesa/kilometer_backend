package com.kilometer.domain.pick.dto;

import com.kilometer.domain.converter.listItem.ItemInfoExtraction;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.FeeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MostPickItemDto implements ItemInfoExtraction {

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
    private Long pickCount;

    @Builder.Default
    private String apiType = API_TYPE;

    @Override
    public String getPresentationImage() {
        return this.thumbnailImageUrl;
    }
}
