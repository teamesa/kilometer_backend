package com.kilometer.domain.archive.like.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LikeResponse {
    private boolean content;
}
