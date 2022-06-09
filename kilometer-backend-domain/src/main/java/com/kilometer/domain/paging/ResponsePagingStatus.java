package com.kilometer.domain.paging;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.kilometer.domain.paging.PagingStatusService.DEFAULT_PAGE_SIZE;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePagingStatus {
    private int nextPage;
    private int currentPage;
    private String query;
    @Builder.Default
    private int pageSize = DEFAULT_PAGE_SIZE;
    private boolean hasNext;
    private long totalContentsCount;
    private int currentContentsCount;

    public static ResponsePagingStatus empty() {
        return ResponsePagingStatus
                .builder()
                .build();
    }
}
