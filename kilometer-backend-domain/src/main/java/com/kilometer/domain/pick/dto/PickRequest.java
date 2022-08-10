package com.kilometer.domain.pick.dto;

import com.kilometer.domain.paging.RequestPagingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickRequest {
    private RequestPagingStatus requestPagingStatus = RequestPagingStatus.empty();
}
