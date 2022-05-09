package com.kilometer.domain.item;

import com.kilometer.domain.item.dto.ItemUpdateRequest;
import com.kilometer.domain.item.dto.SummaryResponse;
import java.time.LocalDate;
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
    @Builder.Default
    private LocalDate createdAt = LocalDate.now();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Builder.Default
    private LocalDate updatedAt = LocalDate.now();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Builder.Default
    private LocalDate startDate = LocalDate.now();
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Builder.Default
    private LocalDate endDate = LocalDate.now();

    private String place;
    private Double latitude;
    private Double longitude;
    @Enumerated(EnumType.STRING)
    private RegionType regionType;

    @Enumerated(EnumType.STRING)
    private FeeType fee;
    private Integer price;
    private String url;

    private String term;

    private String ticketUrl;

    private String time;

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
    }

    public SummaryResponse makeSummaryResponse() {
        return SummaryResponse.builder()
            .type(String.valueOf(this.exhibitionType))
            .progress(this.progressType == ProgressType.ON)
            .title(this.title)
            .term(this.term)
            .place(this.place)
            .lat(this.latitude)
            .lng(this.longitude)
            .feeType((this.fee == FeeType.COST) ? "유료" : "무료")
            .price((this.price == null) ? null : String.valueOf(this.price))
            .ticketUrl((StringUtils.hasText(this.ticketUrl)) ? null : this.ticketUrl)
            .time((StringUtils.hasText(this.time)) ? null : this.time)
            .homePageUrl((StringUtils.hasText(this.url)) ? null : this.url)
            .thumbnailImageUrl((StringUtils.hasText(this.image)) ? null : this.image)
            .build();
    }
}
