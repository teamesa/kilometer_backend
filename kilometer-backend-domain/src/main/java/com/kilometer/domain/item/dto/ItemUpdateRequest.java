package com.kilometer.domain.item.dto;

import com.kilometer.domain.item.ExhibitionType;
import com.kilometer.domain.item.ExposureType;
import com.kilometer.domain.item.FeeType;
import com.kilometer.domain.item.RegionType;
import java.time.LocalDate;
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
public class ItemUpdateRequest {

    private ExhibitionType exhibitionType;
    private ExposureType exposureType;
    private String listImageUrl;
    private String thumbnailImageUrl;
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String term;
    private String placeName;
    private Double latitude;
    private Double longitude;
    private RegionType regionType;
    private FeeType feeType;
    private String price;
    private String homepageUrl;
    private String operatingTime;
    private String ticketUrl;
    private String introduce;
    private List<String> detailImageUrl;
}
