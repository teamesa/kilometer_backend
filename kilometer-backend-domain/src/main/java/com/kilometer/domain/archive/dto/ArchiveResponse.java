package com.kilometer.domain.archive.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kilometer.domain.paging.ResponsePagingStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArchiveResponse {
    private ResponsePagingStatus responsePagingStatus;
    private Double avgStarRating;
    @Builder.Default
    private List<ArchiveInfo> archives = List.of();
}
