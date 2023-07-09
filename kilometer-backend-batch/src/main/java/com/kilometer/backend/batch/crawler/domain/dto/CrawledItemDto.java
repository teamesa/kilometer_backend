package com.kilometer.backend.batch.crawler.domain.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;
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

    private List<String> itemDetailImeags;
}
