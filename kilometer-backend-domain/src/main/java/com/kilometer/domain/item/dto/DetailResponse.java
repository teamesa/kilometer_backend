package com.kilometer.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailResponse {
    private String summary;
    @Builder.Default
    private List<String> photo = List.of();

    public static DetailResponse empty() {
        return DetailResponse
                .builder()
                .build();
    }
}
