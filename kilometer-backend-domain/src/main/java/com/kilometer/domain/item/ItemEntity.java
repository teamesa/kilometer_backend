package com.kilometer.domain.item;

import com.kilometer.domain.item.dto.ItemResponse;
import com.kilometer.domain.item.dto.ItemUpdateRequest;
import com.kilometer.domain.item.dto.SummaryResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "item_entity")
public class ItemEntity {

    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private ExhibitionType exhibitionType;

    @Enumerated(EnumType.STRING)
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
    @Enumerated(EnumType.STRING)
    private RegionType regionType;

    @Enumerated(EnumType.STRING)
    private FeeType fee;
    private String price;
    private String url;

    private String time;

    private String ticketUrl;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "itemDetailEntity")
    private ItemDetail itemDetailEntity;

    public void update(ItemUpdateRequest item) {
        this.exhibitionType = item.getExhibitionType();
        this.progressType = item.getProgressType();
        this.image = item.getImage();
        this.title = item.getTitle();
        this.startDate = item.getStartDate();
        this.endDate = item.getEndDate();
        this.place = item.getPlace();
        this.latitude = item.getLatitude();
        this.longitude = item.getLongitude();
        this.regionType = item.getRegionType();
        this.fee = item.getFee();
        this.price = item.getPrice();
        this.url = item.getUrl();
        this.time = item.getTime();
        this.ticketUrl = item.getTicketUrl();
    }

    public ItemResponse makeResponse() {
        return ItemResponse.builder()
                .id(this.id)
                .exhibitionType(this.exhibitionType)
                .progressType(this.progressType)
                .image(this.image)
                .title(this.title)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .place(this.place)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .regionType(this.regionType)
                .fee(this.fee)
                .price(this.price)
                .url(this.url)
                .time(this.time)
                .ticketUrl(this.ticketUrl)
                .detailImageUrl(this.itemDetailEntity.getImages().stream()
                        .map(DetailImage::getUrl)
                        .collect(Collectors.toList())
                ).introduce(this.itemDetailEntity.getIntroduce())
                .build();
    }

    public SummaryResponse makeSummaryResponse() {
        return SummaryResponse.builder()
            .type(String.valueOf(this.exhibitionType))
            .progress(this.progressType == ProgressType.ON)
            .title(this.title)
            .term(this.startDate + " ~ " + this.endDate)
            .place(this.place)
            .lat(this.latitude)
            .lng(this.longitude)
            .feeType((this.fee == FeeType.COST) ? "유료" : "무료")
            .price((StringUtils.hasText(this.price)) ? null : this.price)
            .ticketUrl((StringUtils.hasText(this.ticketUrl)) ? null : this.ticketUrl)
            .time((StringUtils.hasText(this.time)) ? null : this.time)
            .homePageUrl((StringUtils.hasText(this.url)) ? null : this.url)
            .thumbnailImageUrl((StringUtils.hasText(this.image)) ? null : this.image)
            .build();
    }
}
