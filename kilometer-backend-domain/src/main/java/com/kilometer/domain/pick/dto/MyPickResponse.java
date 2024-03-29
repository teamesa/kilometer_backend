package com.kilometer.domain.pick.dto;

import com.kilometer.domain.paging.ResponsePagingStatus;
import com.kilometer.domain.converter.listItem.dto.ListItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class MyPickResponse {

    private long count;
    @Builder.Default
    private ResponsePagingStatus responsePagingStatus = ResponsePagingStatus.empty();
    @Builder.Default
    private List<ListItem> contents = List.of();
}
