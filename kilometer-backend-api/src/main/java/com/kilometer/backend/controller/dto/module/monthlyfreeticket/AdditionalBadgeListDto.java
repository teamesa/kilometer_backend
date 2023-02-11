package com.kilometer.backend.controller.dto.module.monthlyfreeticket;

import com.kilometer.domain.item.dto.MonthlyFreeTicketDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AdditionalBadgeListDto {

    private final List<TypeBadgeDto> additionalBadgeList;

    static AdditionalBadgeListDto from(MonthlyFreeTicketDto monthlyFreeTicketDto) {
        return AdditionalBadgeListDto.builder()
                .additionalBadgeList(List.of(
                        TypeBadgeDto.of(monthlyFreeTicketDto.getExposureType().name(), false),
                        TypeBadgeDto.of(monthlyFreeTicketDto.getFeeType().getDescription(), false)
                ))
                .build();
    }

    @Override
    public String toString() {
        return "AdditionalBadgeListDto{" +
                "additionalBadgeList=" + additionalBadgeList +
                '}';
    }
}
