package com.kilometer.domain.item.dto;

import com.kilometer.domain.item.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemListResponse {
    private Long id;
    private ExhibitionType exhibitionType;
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

    public ItemListResponse(ItemEntity itemEntity) {
        this.id = itemEntity.getId();
        this.exhibitionType = itemEntity.getExhibitionType();
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
