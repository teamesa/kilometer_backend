package com.kilometer.domain.item.dto;

import com.kilometer.domain.item.ExhibitionType;
import com.kilometer.domain.item.FeeType;
import com.kilometer.domain.item.ProgressType;
import com.kilometer.domain.item.RegionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {

    private Long id;
    private ExhibitionType exhibitionType;
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
    private RegionType regionType;
    private FeeType fee;
    private String price;
    private String url;
    private String time;
    private String ticketUrl;
    private String introduce;
    private List<String> detailImageUrl;

    public ItemResponse(ExhibitionType exhibitionType, ProgressType progressType, LocalDate startDate, LocalDate endDate, RegionType regionType, FeeType fee) {
        this.exhibitionType = exhibitionType;
        this.progressType = progressType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.regionType = regionType;
        this.fee = fee;
    }
}