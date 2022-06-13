package com.kilometer.domain.item.dto;

import com.kilometer.domain.item.ExhibitionType;
import com.kilometer.domain.item.ExposureType;
import com.kilometer.domain.item.FeeType;
import com.kilometer.domain.item.ItemDetail;
import com.kilometer.domain.item.ItemDetailImage;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.item.RegionType;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemSaveRequest {

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
    private List<String> detailImageUrls = List.of();

    public ItemDetail makeItemDetail() {
        return ItemDetail.builder()
            .introduce(this.introduce)
            .build();
    }

    public List<ItemDetailImage> makeItemDetailImage() {
        return this.detailImageUrls.stream()
            .map(imageUrl -> ItemDetailImage.makeEntity(imageUrl))
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
