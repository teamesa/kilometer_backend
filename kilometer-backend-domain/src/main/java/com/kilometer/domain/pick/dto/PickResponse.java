package com.kilometer.domain.pick.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PickResponse {
    private boolean content;
}
