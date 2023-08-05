package com.kilometer.domain.crawledItem.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.kilometer.domain.crawledItem.CrawledItem;
import com.kilometer.domain.crawledItem.CrawledItemDetailImage;
import com.kilometer.domain.crawledItem.CrawledItemDetailImage.CrawledItemDetailImageBuilder;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.item.enumType.RegionType;
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
public class CrawledItemDto {

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

    public CrawledItem toEntity() {
        CrawledItem crawledItem = CrawledItem.builder()
                .exhibitionType(ExhibitionType.valueOf(exhibitionType))
                .exposureType(ExposureType.valueOf(exposureType))
                .regionType(RegionType.valueOf(regionType))
                .feeType(FeeType.valueOf(feeType))
                .listImageUrl(listImageUrl)
                .thumbnailImageUrl(thumbnailImageUrl)
                .title(title)
                .startDate(startDate)
                .endDate(endDate)
                .placeName(placeName)
                .latitude(latitude)
                .longitude(longitude)
                .price(price)
                .homepageUrl(homepageUrl)
                .operatingTime(operatingTime)
                .ticketUrl(ticketUrl)
                .introduce(introduce)
                .source(source)
                .build();
        itemDetailImages.stream()
                .map(image -> CrawledItemDetailImage.builder().url(image).build())
                .collect(Collectors.toList())
                .forEach(crawledItem::addCralwedItemDetailImage);
        return crawledItem;
    }
}
