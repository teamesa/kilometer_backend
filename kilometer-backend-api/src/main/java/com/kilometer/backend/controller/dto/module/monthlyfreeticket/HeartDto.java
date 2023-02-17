package com.kilometer.backend.controller.dto.module.monthlyfreeticket;

import com.kilometer.domain.item.dto.MonthlyFreeTicketDto;
import com.kilometer.domain.util.ApiUrlUtils;
import com.kilometer.domain.util.FrontUrlUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HeartDto {

    private final boolean heartClicked;
    private final String link;
    private final Long id;

    static HeartDto from(MonthlyFreeTicketDto monthlyFreeTicketDto) {
        return HeartDto.builder()
                .heartClicked(monthlyFreeTicketDto.isHearted())
                .link(ApiUrlUtils.getPickItemUrl(monthlyFreeTicketDto.getItemId()))
                .id(monthlyFreeTicketDto.getItemId())
                .build();
    }

    @Override
    public String toString() {
        return "HeartDto{" +
                "heartClicked=" + heartClicked +
                ", link='" + link + '\'' +
                ", id=" + id +
                '}';
    }
}
