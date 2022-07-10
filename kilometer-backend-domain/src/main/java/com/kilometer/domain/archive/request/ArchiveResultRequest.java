package com.kilometer.domain.archive.request;

import com.kilometer.domain.paging.RequestPagingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveResultRequest {
    private RequestPagingStatus requestPagingStatus;
}
