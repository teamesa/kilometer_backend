package com.kilometer.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FeeType {

    FREE("무료"), COST("유료");

    private final String description;
}
