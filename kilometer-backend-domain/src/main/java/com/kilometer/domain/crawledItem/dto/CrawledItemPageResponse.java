package com.kilometer.domain.crawledItem.dto;

import com.kilometer.domain.crawledItem.CrawledItem;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
public class CrawledItemPageResponse {

   private int totalPage;
   private int currentPage;
   private List<CrawledItemResponse> crawledItemResponses;

   public static CrawledItemPageResponse from(final int totalPage, final int currentPage, final List<CrawledItem> crawledItem) {
      List<CrawledItemResponse> crawledItemResponses = crawledItem.stream()
              .map(CrawledItemResponse::of)
              .collect(Collectors.toList());

      return CrawledItemPageResponse.builder()
              .totalPage(totalPage)
              .currentPage(currentPage)
              .crawledItemResponses(crawledItemResponses)
              .build();
   }
}
