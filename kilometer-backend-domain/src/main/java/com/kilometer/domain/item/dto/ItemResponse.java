package com.kilometer.domain.item.dto;

import com.kilometer.domain.item.ExhibitionType;
import com.kilometer.domain.item.ExposureType;
import com.kilometer.domain.item.FeeType;
import com.kilometer.domain.item.RegionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {

    private Long id;

    @Builder.Default
    private ExhibitionType exhibitionType = ExhibitionType.EXHIBITION;
    @Builder.Default
    private ExposureType exposureType = ExposureType.ON;
    @Builder.Default
    private RegionType regionType = RegionType.SEOUL;
    @Builder.Default
    private FeeType feeType = FeeType.FREE;

    private String listImageUrl;
    private String thumbnailImageUrl;

    private String title;

    @Builder.Default
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate = LocalDate.now();

    @Builder.Default
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate = LocalDate.now();

    private String placeName;

    private Double latitude;
    private Double longitude;

    private String price;
    private String homepageUrl;
    private String operatingTime;
    private String ticketUrl;

    // ItemDetail & DetailImage
    private String introduce;
    private List<String> detailImageUrls;

    public static ItemResponse empty() {
        return ItemResponse.builder()
            .build();
    }
}