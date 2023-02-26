package com.kilometer.domain.item.dto;

import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.item.enumType.RegionType;
import com.kilometer.domain.item.itemDetail.ItemDetail;
import com.kilometer.domain.item.itemDetailImage.ItemDetailImage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemUpdateRequest implements DetailRequest {

    private ExhibitionType exhibitionType;
    private ExposureType exposureType;
    private RegionType regionType;
    private FeeType feeType;

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
    @Builder.Default
    private List<String> detailImageUrls = new ArrayList<>();

    @Builder.Default
    private List<Long> deleteDetailImages = new ArrayList<>();

    public List<ItemDetailImage> makeUpdateItemDetailImage() {
        return this.detailImageUrls.stream()
            .filter(StringUtils::hasText)
            .map(ItemDetailImage::makeEntity)
            .collect(Collectors.toList());
    }
}
