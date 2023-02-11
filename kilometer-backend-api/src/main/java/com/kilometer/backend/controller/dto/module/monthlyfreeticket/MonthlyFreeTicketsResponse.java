package com.kilometer.backend.controller.dto.module.monthlyfreeticket;

import com.kilometer.backend.util.StringConvertor;
import com.kilometer.domain.homeModules.ModuleResponseDto;
import com.kilometer.domain.homeModules.modules.monthlyFreeTicket.dto.MonthlyFreeTicketResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MonthlyFreeTicketsResponse {

    private final String topTitle;
    private final String bottomTitle;
    private final List<ContentDto> contents;

    public static MonthlyFreeTicketsResponse from(ModuleResponseDto<MonthlyFreeTicketResponse> moduleResponseDto) {
        MonthlyFreeTicketResponse monthlyFreeTicketResponse = moduleResponseDto.getData();
        List<ContentDto> contents = monthlyFreeTicketResponse.getMonthlyFreeTicketDtos()
                .stream()
                .map(ContentDto::from)
                .collect(Collectors.toList());
        return MonthlyFreeTicketsResponse.builder()
                .topTitle(StringConvertor.convertNullToBlank(monthlyFreeTicketResponse.getTopTitle()))
                .bottomTitle(StringConvertor.convertNullToBlank(monthlyFreeTicketResponse.getBottomTitle()))
                .contents(contents)
                .build();
    }

    @Override
    public String toString() {
        return "MonthlyFreeTicketsResponse{" +
                "topTitle='" + topTitle + '\'' +
                ", bottomTitle='" + bottomTitle + '\'' +
                ", contents=" + contents +
                '}';
    }
}
