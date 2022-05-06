package com.kilometer.backend.controller.dto;

import com.kilometer.domain.item.ExhibitionType;
import com.kilometer.domain.item.FeeType;
import com.kilometer.domain.item.ProgressType;
import com.kilometer.domain.item.RegionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class ItemForm {
    private ExhibitionType exhibitionType;
    private ProgressType progressType;
    private MultipartFile image;
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
    private Integer price;
    private String url;
}
