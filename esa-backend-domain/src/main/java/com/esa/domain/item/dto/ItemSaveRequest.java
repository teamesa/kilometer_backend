package com.esa.domain.item.dto;

import com.esa.domain.item.FeeType;
import com.esa.domain.item.ProgressType;
import com.esa.domain.item.RegionType;
import com.esa.domain.item.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemSaveRequest {
    private ProgressType progressType;
    private String image;
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
