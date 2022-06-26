package com.kilometer.domain.item.dto;

import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.item.enumType.RegionType;
import com.kilometer.domain.item.itemDetail.ItemDetail;
import com.kilometer.domain.item.itemDetailImage.ItemDetailImage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {

    private ExhibitionType exhibitionType;
    private ExposureType exposureType;
    private RegionType regionType;
    private FeeType feeType;

    private String listImageUrl;
    private String thumbnailImageUrl;
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String placeName;

    private Double latitude;
    private Double longitude;

    private String price;
    private String homepageUrl;
    private String operatingTime;
    private String ticketUrl;
    private String introduce;
    @Builder.Default
    private List<String> detailImageUrls = new ArrayList<>();

    public ItemDetail makeItemDetail() {
        return ItemDetail.builder()
            .introduce(this.introduce)
            .build();
    }

    public List<ItemDetailImage> makeItemDetailImage() {
        return this.detailImageUrls.stream()
            .filter(StringUtils::hasText)
            .map(ItemDetailImage::makeEntity)
            .collect(Collectors.toList());
    }

    public ItemEntity makeItemEntity() {
        return ItemEntity.builder()
            .exhibitionType(this.getExhibitionType())
            .exposureType(this.getExposureType())
            .listImageUrl(this.getListImageUrl())
            .thumbnailImageUrl(this.getThumbnailImageUrl())
            .title(this.getTitle())
            .startDate(this.getStartDate())
            .endDate(this.getEndDate())
            .placeName(this.getPlaceName())
            .latitude(this.getLatitude())
            .longitude(this.getLongitude())
            .regionType(this.getRegionType())
            .feeType(this.getFeeType())
            .price(this.getPrice())
            .homepageUrl(this.getHomepageUrl())
            .operatingTime(this.getOperatingTime())
            .ticketUrl(this.getTicketUrl())
            .build();
    }
}
