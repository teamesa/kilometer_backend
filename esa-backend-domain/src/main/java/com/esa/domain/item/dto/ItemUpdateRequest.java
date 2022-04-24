package com.esa.domain.item.dto;

import com.esa.domain.item.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemUpdateRequest {
    private ProgressType progressType;
    private String image;
    private String title;
    private String term;
    private String place;
    private Double latitude;
    private Double longitude;
    private RegionType regionType;
    private FeeType fee;
    private Integer price;
    private String url;

    public ItemUpdateRequest(ItemEntity itemEntity) {
        this.progressType = itemEntity.getProgressType();
        this.image = itemEntity.getImage();
        this.title = itemEntity.getTitle();
        this.term = itemEntity.getTerm();
        this.place = itemEntity.getPlace();
        this.latitude = itemEntity.getLatitude();
        this.longitude = itemEntity.getLongitude();
        this.regionType = itemEntity.getRegionType();
        this.fee = itemEntity.getFee();
        this.price = itemEntity.getPrice();
        this.url = itemEntity.getUrl();
    }
}
