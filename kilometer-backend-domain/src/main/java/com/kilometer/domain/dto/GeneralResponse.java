package com.kilometer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GeneralResponse<T> {

    private String title;
    private T contents;
}
