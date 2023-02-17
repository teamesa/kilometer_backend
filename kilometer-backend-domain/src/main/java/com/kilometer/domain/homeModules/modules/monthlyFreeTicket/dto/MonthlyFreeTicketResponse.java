package com.kilometer.domain.homeModules.modules.monthlyFreeTicket.dto;

import com.kilometer.domain.item.dto.MonthlyFreeTicketDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class MonthlyFreeTicketResponse {

    private String topTitle;
    private String bottomTitle;
    private List<MonthlyFreeTicketDto> monthlyFreeTicketDtos;

    public static MonthlyFreeTicketResponse of(String topTitle,
                                               String bottomTitle,
                                               List<MonthlyFreeTicketDto> monthlyFreeTicketDtos) {
        return MonthlyFreeTicketResponse.builder()
                .topTitle(topTitle)
                .bottomTitle(bottomTitle)
                .monthlyFreeTicketDtos(monthlyFreeTicketDtos)
                .build();
    }

    @Override
    public String toString() {
        return "MonthlyFreeTicketResponse{" +
                "topTitle='" + topTitle + '\'' +
                ", bottomTitle='" + bottomTitle + '\'' +
                ", monthlyFreeTicketDtos=" + monthlyFreeTicketDtos +
                '}';
    }
}
