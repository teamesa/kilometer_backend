package com.kilometer.domain.item.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SummaryResponse {
  private String type;
  private boolean progress; // 빠져도 될 것 같음. 전시 상태 ON/OFF
  private String title;
  private String term;
  private String place;
  private double lat;
  private double lng;
  private String feeType;
  private String price;
  private String ticketUrl;
  private String time;
  private String homePageUrl;
  private String thumbnailImageUrl;
}
