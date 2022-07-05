package com.kilometer.domain.archive.dto;

import com.kilometer.domain.paging.ResponsePagingStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MyArchiveResponse {

    private String title;
    private ResponsePagingStatus responsePagingStatus;
    private List<MyArchiveInfo> contents;
}
