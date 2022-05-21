package com.kilometer.domain.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemDetailResponse<T> {
    private String title;
    private T contents;
}
