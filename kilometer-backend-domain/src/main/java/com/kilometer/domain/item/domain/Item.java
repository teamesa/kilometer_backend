package com.kilometer.domain.item.domain;

import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.item.enumType.RegionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Item {

    private Long id;
    private ExposureType exposureType;
    private ExhibitionType exhibitionType;
    private RegionType regionType;
    private FeeType feeType;
    private String listImageUrl;
    private String detailImageUrl;
    private String title;
    private String placeName;

    private LocalDate startDate;
    private LocalDate endDate;

    private Double latitude;
    private Double longitude;

    private String price;
    private String homepageUrl;
    private String operatingTime;
    private String ticketUrl;
    private int pickCount;

    public static Item of(ItemEntity entity) {
        return Item.builder()
            .id(entity.getId())
            .exposureType(entity.getExposureType())
            .exhibitionType(entity.getExhibitionType())
            .regionType(entity.getRegionType())
            .feeType(entity.getFeeType())
            .listImageUrl(entity.getListImageUrl())
            .detailImageUrl(entity.getThumbnailImageUrl())
            .title(entity.getTitle())
            .placeName(entity.getPlaceName())
            .startDate(entity.getStartDate())
            .endDate(entity.getEndDate())
            .latitude(entity.getLatitude())
            .longitude(entity.getLongitude())
            .price(entity.getPrice())
            .operatingTime(entity.getOperatingTime())
            .homepageUrl(entity.getHomepageUrl())
            .ticketUrl(entity.getTicketUrl())
            .pickCount(entity.getPickCount())
            .build();
    }
}
