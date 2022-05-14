package com.kilometer.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.kilometer.domain.search.dto.SearchDtoConstants.DEFAULT_PAGE_SIZE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePagingStatus {
    private int nextPage;
    private int currentPage;
    @Builder.Default
    private int pageSize = DEFAULT_PAGE_SIZE;
    private boolean hasNext;
    private int totalContentsCount;
    private int currentContentsCount;

    public static ResponsePagingStatus empty() {
        return ResponsePagingStatus
                .builder()
                .build();
    }
}
