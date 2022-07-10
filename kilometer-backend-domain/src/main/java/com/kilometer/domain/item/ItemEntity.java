package com.kilometer.domain.item;

import com.kilometer.domain.item.dto.ItemResponse;
import com.kilometer.domain.item.dto.ItemUpdateRequest;
import com.kilometer.domain.item.dto.ItemUpdateResponse;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.item.enumType.RegionType;
import com.kilometer.domain.item.itemDetail.ItemDetail;
import com.kilometer.domain.item.itemDetailImage.ItemDetailImage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Entity
@Builder
@Where(clause = "isDeleted=false")
@SQLDelete(sql = "UPDATE `item` SET isDeleted = true where id=?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "item")
public class ItemEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ExhibitionType exhibitionType = ExhibitionType.EXHIBITION;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ExposureType exposureType = ExposureType.ON;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private RegionType regionType = RegionType.SEOUL;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private FeeType feeType = FeeType.FREE;

    private String listImageUrl;
    private String thumbnailImageUrl;
    private String title;
    private String placeName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;


    private Double latitude;
    private Double longitude;

    private String price;
    private String homepageUrl;

    private String operatingTime;

    private String ticketUrl;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
    @Builder.Default
    private boolean isDeleted = false;
    @Builder.Default
    private int pickCount = 0;

    @OneToOne(mappedBy = "item", fetch = FetchType.LAZY)
    private ItemDetail itemDetail;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    @Builder.Default
    private List<ItemDetailImage> itemDetailImages = new ArrayList<>();

    public void update(ItemUpdateRequest item) {
        this.exhibitionType = item.getExhibitionType();
        this.exposureType = item.getExposureType();
        this.listImageUrl = item.getListImageUrl();
        this.thumbnailImageUrl = item.getThumbnailImageUrl();
        this.title = item.getTitle();
        this.startDate = item.getStartDate();
        this.endDate = item.getEndDate();
        this.placeName = item.getPlaceName();
        this.latitude = item.getLatitude();
        this.longitude = item.getLongitude();
        this.regionType = item.getRegionType();
        this.feeType = item.getFeeType();
        this.price = item.getPrice();
        this.homepageUrl = item.getHomepageUrl();
        this.operatingTime = item.getOperatingTime();
        this.ticketUrl = item.getTicketUrl();
    }

    public ItemResponse makeResponse() {
        return ItemResponse.builder()
            .id(this.id)
            .exhibitionType(this.exhibitionType)
            .exposureType(this.exposureType)
            .listImageUrl(this.listImageUrl)
            .thumbnailImageUrl(this.thumbnailImageUrl)
            .title(this.title)
            .startDate(this.startDate)
            .endDate(this.endDate)
            .placeName(this.placeName)
            .latitude(this.latitude)
            .longitude(this.longitude)
            .regionType(this.regionType)
            .feeType(this.feeType)
            .price(this.price)
            .homepageUrl(this.homepageUrl)
            .operatingTime(this.operatingTime)
            .itemPickCount(this.pickCount)
            .ticketUrl(this.ticketUrl)
            .detailImageUrls(Optional.ofNullable(this.itemDetailImages)
                .map(itemDetailImages -> itemDetailImages.stream()
                    .map(ItemDetailImage::getImageUrl)
                    .collect(Collectors.toList()))
                .orElse(new ArrayList<>()))
            .introduce(Optional.ofNullable(this.itemDetail)
                .map(ItemDetail::getIntroduce)
                .orElse(""))
            .build();
    }

    public ItemUpdateResponse makeUpdateResponse() {
        return ItemUpdateResponse.builder()
            .id(this.id)
            .exhibitionType(this.exhibitionType)
            .exposureType(this.exposureType)
            .listImageUrl(this.listImageUrl)
            .thumbnailImageUrl(this.thumbnailImageUrl)
            .title(this.title)
            .startDate(this.startDate)
            .endDate(this.endDate)
            .placeName(this.placeName)
            .latitude(this.latitude)
            .longitude(this.longitude)
            .regionType(this.regionType)
            .feeType(this.feeType)
            .price(this.price)
            .homepageUrl(this.homepageUrl)
            .operatingTime(this.operatingTime)
            .ticketUrl(this.ticketUrl)
            .detailImageUrlsAndIndex(Optional.ofNullable(this.itemDetailImages)
                .map(itemDetailImages -> itemDetailImages.stream()
                    .map(ItemDetailImage::getItemDetailImage)
                    .collect(Collectors.toList()))
                .orElse(new ArrayList<>()))
            .introduce(Optional.ofNullable(this.itemDetail)
                .map(ItemDetail::getIntroduce)
                .orElse(""))
            .build();
    }

    public void setItemDetail(ItemDetail itemDetail) {
        this.itemDetail = itemDetail;
    }

    public ItemEntity plusPickCount() {
        this.pickCount++;
        return this;
    }

    public ItemEntity minusPickCount() {
        this.pickCount = Math.max(this.pickCount - 1, 0);
        return this;
    }
}
