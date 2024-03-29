package com.kilometer.domain.item.dto;

import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.item.enumType.RegionType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    private int itemPickCount;
    private String regAccount;
    private String udtAccount;

    // ItemDetail & DetailImage
    private String introduce;
    @Builder.Default
    private List<String> detailImageUrls = new ArrayList<>();
    private String source;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ItemResponse empty() {
        return ItemResponse.builder()
            .build();
    }
}