package com.kilometer.backend.controller.dto;

import com.kilometer.domain.item.ExhibitionType;
import com.kilometer.domain.item.ExposureType;
import com.kilometer.domain.item.FeeType;
import com.kilometer.domain.item.RegionType;
import com.kilometer.domain.item.dto.ItemSaveRequest;
import com.kilometer.domain.item.dto.ItemUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ItemForm {

    private ExhibitionType exhibitionType;
    private ExposureType exposureType;
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
    private String price;
    private String url;
    private String time;
    private String ticketUrl;
    private String introduce;
    private List<MultipartFile> detailImageUrl;
    private List<Long> deleteImageIndex;

    public ItemSaveRequest makeItemSaveRequest(String s3ImageUrl, ArrayList<String> multiS3ImageUrl) {
        return ItemSaveRequest.builder()
                .exhibitionType(this.exhibitionType)
                .exposureType(this.exposureType)
                .image(s3ImageUrl)
                .title(this.title)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .regionType(this.regionType)
                .place(this.place)
                .fee(this.fee)
                .price(this.price)
                .url(this.url)
                .time(this.time)
                .ticketUrl(this.ticketUrl)
                .introduce(this.introduce)
                .detailImageUrl(multiS3ImageUrl)
                .build();
    }

    public ItemUpdateRequest makeItemUpdateRequest(String s3ImageUrl, ArrayList<String> multiS3ImageUrl, ArrayList<Long> deleteImage) {
        return ItemUpdateRequest.builder()
                .exhibitionType(this.exhibitionType)
                .exposureType(this.exposureType)
                .image(s3ImageUrl)
                .title(this.title)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .regionType(this.regionType)
                .place(this.place)
                .fee(this.fee)
                .price(this.price)
                .url(this.url)
                .time(this.time)
                .ticketUrl(this.ticketUrl)
                .introduce(this.introduce)
                .detailImageUrl(multiS3ImageUrl)
                .deleteImage(deleteImage)
                .build();
    }
}
