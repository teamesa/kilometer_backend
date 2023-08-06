package com.kilometer.domain.crawledItem.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.kilometer.domain.crawledItem.CrawledItem;
import com.kilometer.domain.crawledItem.CrawledItemDetailImage;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class CrawledItemResponse {

    private String exhibitionType;
    private String exposureType;
    private String regionType;
    private String feeType;

    private String listImageUrl;
    private String thumbnailImageUrl;
    private String title;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
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

    private List<String> itemDetailImages;

    public static CrawledItemResponse of(final CrawledItem crawledItem) {
        List<String> detailImagesUrl = crawledItem.getCrawledItemDetailImages()
                .stream()
                .map(CrawledItemDetailImage::getUrl)
                .collect(Collectors.toList());

        return CrawledItemResponse.builder()
                .exhibitionType(crawledItem.getExhibitionType().getDescription())
                .exposureType(crawledItem.getExposureType().getDescription())
                .regionType(crawledItem.getRegionType().getDescription())
                .feeType(crawledItem.getFeeType().getDescription())
                .listImageUrl(crawledItem.getListImageUrl())
                .thumbnailImageUrl(crawledItem.getHomepageUrl())
                .title(crawledItem.getTitle())
                .startDate(crawledItem.getStartDate())
                .endDate(crawledItem.getEndDate())
                .placeName(crawledItem.getPlaceName())
                .latitude(crawledItem.getLatitude())
                .longitude(crawledItem.getLongitude())
                .price(crawledItem.getPrice())
                .homepageUrl(crawledItem.getHomepageUrl())
                .operatingTime(crawledItem.getHomepageUrl())
                .ticketUrl(crawledItem.getTicketUrl())
                .introduce(crawledItem.getIntroduce())
                .source(crawledItem.getSource())
                .itemDetailImages(detailImagesUrl)
                .build();
    }
}
