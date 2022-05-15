package com.kilometer.domain.paging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.kilometer.domain.paging.PagingStatusService.DEFAULT_PAGE_SIZE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestPagingStatus {
    private int pageNumber;
    @Builder.Default
    private int pageSize = DEFAULT_PAGE_SIZE;
    private int currentContentsCount;

    public static RequestPagingStatus empty() {
        return RequestPagingStatus
                .builder()
                .build();
    }
}
