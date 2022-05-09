package com.kilometer.domain.item.dto;

import com.kilometer.domain.item.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemAndDetailListResponse {

    private ItemEntity itemEntity;
}
