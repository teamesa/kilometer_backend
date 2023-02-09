package com.kilometer.domain.item.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemInfoResponse {
    private String type;
    private String title;
    private String term;
    private String place;
    private Double lat;
    private Double lng;
    private String feeType;
    private String price;
    private String ticketUrl;
    private String time;
    private String homePageUrl;
    private String detailImageUrl;
    private String listImageUrl;
    private String summary;
    @Builder.Default
    private List<String> photo = List.of();

    private ItemInfoAdditionalInfo itemInfoAdditionalInfo;
}
