package com.kilometer.domain.item.dto;

import com.kilometer.domain.item.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {

    private ExhibitionType exhibitionType;
    private ProgressType progressType;
    private String image;
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String place;
    private Double latitude;
    private Double longitude;
    private RegionType regionType;
    private FeeType fee;
    private Integer price;
    private String url;

    public ItemResponse(ItemEntity itemEntity) {
        this.exhibitionType = itemEntity.getExhibitionType();
        this.progressType = itemEntity.getProgressType();
        this.image = itemEntity.getImage();
        this.title = itemEntity.getTitle();
        this.startDate = itemEntity.getStartDate();
        this.endDate = itemEntity.getEndDate();
        this.place = itemEntity.getPlace();
        this.latitude = itemEntity.getLatitude();
        this.longitude = itemEntity.getLongitude();
        this.regionType = itemEntity.getRegionType();
        this.fee = itemEntity.getFee();
        this.price = itemEntity.getPrice();
        this.url = itemEntity.getUrl();
    }

    public ItemResponse(ExhibitionType exhibitionType, ProgressType progressType, RegionType regionType, FeeType fee) {
        this.exhibitionType = exhibitionType;
        this.progressType = progressType;
        this.regionType = regionType;
        this.fee = fee;
    }
}