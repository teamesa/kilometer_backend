package com.kilometer.domain.homeModules.modules.monthlyFreeTicket;

import com.kilometer.domain.homeModules.modules.monthlyFreeTicket.dto.MonthlyFreeTicketEntityDto;
import java.time.LocalDateTime;
import java.util.List;

interface MonthlyFreeItemRepository {

    List<MonthlyFreeTicketEntityDto> findRand5ByUserIdAndRequestTime(Long userId, LocalDateTime requestTime);

}
