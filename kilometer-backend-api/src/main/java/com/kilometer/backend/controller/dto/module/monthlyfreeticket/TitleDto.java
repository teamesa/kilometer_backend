package com.kilometer.backend.controller.dto.module.monthlyfreeticket;

import com.kilometer.domain.item.dto.MonthlyFreeTicketDto;
import com.kilometer.domain.util.FrontUrlUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TitleDto {

    private final String text;
    private final String link;

    static TitleDto from(MonthlyFreeTicketDto monthlyFreeTicketDto) {
        return TitleDto.builder()
                .text(monthlyFreeTicketDto.getTitle())
                .link(FrontUrlUtils.getFrontDetailUrlPattern(monthlyFreeTicketDto.getItemId()))
                .build();
    }

    @Override
    public String toString() {
        return "TitleDto{" +
                "text='" + text + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
