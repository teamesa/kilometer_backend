package com.esa.backend.controller.dto;

import com.esa.domain.item.FeeType;
import com.esa.domain.item.ProgressType;
import com.esa.domain.item.RegionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
public class ItemForm {
    private ProgressType progressType;
    private MultipartFile image;
    private String title;
    private String term;
    private String place;
    private Double latitude;
    private Double longitude;
    private RegionType regionType;
    private FeeType fee;
    private Integer price;
    private String url;
}