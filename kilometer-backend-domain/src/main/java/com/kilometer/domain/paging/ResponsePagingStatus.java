package com.kilometer.domain.paging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePagingStatus {
    private static int DEFAULT_PAGE_SIZE = 10;
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
