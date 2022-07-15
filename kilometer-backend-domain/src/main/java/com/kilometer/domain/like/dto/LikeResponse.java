package com.kilometer.domain.like.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LikeResponse {
    private boolean content;
}
