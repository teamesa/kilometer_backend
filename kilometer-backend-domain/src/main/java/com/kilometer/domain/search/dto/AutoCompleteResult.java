package com.kilometer.domain.search.dto;

import com.kilometer.domain.paging.ResponsePagingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AutoCompleteResult {
    @Builder.Default
    private ResponsePagingStatus responsePagingStatus = ResponsePagingStatus.empty();
    @Builder.Default
    private List<AutoCompleteItem> contents = List.of();
}
