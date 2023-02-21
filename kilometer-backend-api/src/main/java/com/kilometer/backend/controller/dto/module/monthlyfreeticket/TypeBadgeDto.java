package com.kilometer.backend.controller.dto.module.monthlyfreeticket;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TypeBadgeDto {

    private final String text;
    private final boolean typeBadge;

    static TypeBadgeDto of(String text, boolean isTypeBadge) {
        return TypeBadgeDto.builder()
                .text(text)
                .typeBadge(isTypeBadge)
                .build();
    }

    @Override
    public String toString() {
        return "TypeBadgeDto{" +
                "text='" + text + '\'' +
                ", typeBadge=" + typeBadge +
                '}';
    }
}
