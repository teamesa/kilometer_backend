package com.kilometer.domain.item.dto;

import com.kilometer.domain.item.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemSaveRequest {
    private ExhibitionType exhibitionType;
    private ExposureType exposureType;
    private RegionType regionType;
    private FeeType fee;

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
    private String time;
    private String ticketUrl;
    private String introduce;
    @Builder.Default
    private List<String> detailImageUrls = List.of();

    public ItemDetail makeItemDetail() {
        return ItemDetail.builder()
                .introduce(this.introduce)
                .build();
    }

    public DetailImage makeDetailImage(ItemDetail itemDetail, int index) {
        return DetailImage.builder()
                .url(this.detailImageUrls.get(index))
                .itemDetailEntity(itemDetail)
                .build();
    }

    public ItemEntity makeItemEntity(ItemDetail itemDetail) {
        return ItemEntity.builder()
                .exhibitionType(this.getExhibitionType())
                .exposureType(this.getExposureType())
                .image(this.getListImageUrl())
                .title(this.getTitle())
                .startDate(this.getStartDate())
                .endDate(this.getEndDate())
                .place(this.getPlaceName())
                .latitude(this.getLatitude())
                .longitude(this.getLongitude())
                .regionType(this.getRegionType())
                .place(this.getPlaceName())
                .fee(this.getFee())
                .price(this.getPrice())
                .url(this.getHomepageUrl())
                .time(this.getTime())
                .ticketUrl(this.getTicketUrl())
                .itemDetailEntity(itemDetail)
                .build();
    }
}
