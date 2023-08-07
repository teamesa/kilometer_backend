package com.kilometer.domain.crawledItem;

import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.item.enumType.RegionType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@SQLDelete(sql = "UPDATE `crawled_item` SET isDeleted = true where id=?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "crawled_item")
public class CrawledItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ExhibitionType exhibitionType;

    @Enumerated(EnumType.STRING)
    private ExposureType exposureType;

    @Enumerated(EnumType.STRING)
    private RegionType regionType;

    @Enumerated(EnumType.STRING)
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
    private String source;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder.Default
    private boolean isDeleted = false;


    @OneToMany(mappedBy = "crawledItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CrawledItemDetailImage> crawledItemDetailImages = new ArrayList<>();

    public void addCralwedItemDetailImage(CrawledItemDetailImage crawledItemDetailImage) {
        crawledItemDetailImages.add(crawledItemDetailImage);
        crawledItemDetailImage.setCrawledItem(this);
    }

    @Override
    public String toString() {
        return "CrawledItem{" +
                "id=" + id +
                ", exhibitionType='" + exhibitionType + '\'' +
                ", exposureType='" + exposureType + '\'' +
                ", regionType='" + regionType + '\'' +
                ", feeType='" + feeType + '\'' +
                ", listImageUrl='" + listImageUrl + '\'' +
                ", thumbnailImageUrl='" + thumbnailImageUrl + '\'' +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", placeName='" + placeName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", price='" + price + '\'' +
                ", homepageUrl='" + homepageUrl + '\'' +
                ", operatingTime='" + operatingTime + '\'' +
                ", ticketUrl='" + ticketUrl + '\'' +
                ", introduce='" + introduce + '\'' +
                ", source='" + source + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
