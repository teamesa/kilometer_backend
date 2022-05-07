package com.kilometer.domain.item;

import com.kilometer.domain.item.dto.ItemUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private Integer price;
    private String url;

    private String time;

    private String ticketUrl;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

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
}
