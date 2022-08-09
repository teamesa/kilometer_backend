package com.kilometer.domain.item.dto;

import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.FeeType;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemInfoDto {

    private Long id;
    private ExhibitionType exhibitionType;
    private FeeType feeType;
    private String title;
    private String thumbnailImageUrl;
    private String listImageUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private String placeName;
    private Double latitude;
    private Double longitude;
    private String price;
    private String ticketUrl;
    private String operatingTime;
    private String homepageUrl;
    private int pickCount;
    private boolean isHeart;
}
