package com.kilometer.domain.homeModules.modules.monthlyFreeTicket;

import com.kilometer.domain.converter.listItem.ListItemAggregateConverter;
import com.kilometer.domain.converter.listItem.dto.ListItem;
import com.kilometer.domain.homeModules.ModuleParamDto;
import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.homeModules.modules.ModuleHandler;
import com.kilometer.domain.homeModules.modules.dto.ModuleDto;
import com.kilometer.domain.homeModules.modules.monthlyFreeTicket.dto.MonthlyFreeTicketDto;
import com.kilometer.domain.homeModules.modules.monthlyFreeTicket.dto.MonthlyFreeTicketResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MonthlyFreeItemHandler implements ModuleHandler {

    private final MonthlyFreeItemRepository monthlyFreeItemRepository;
    private final ListItemAggregateConverter listItemAggregateConverter;

    @Override
    public boolean supports(ModuleType moduleType) {
        return moduleType == ModuleType.MONTHLY_FREE_ITEM;
    }

    @Override
    public Object generator(ModuleParamDto paramDto) throws RuntimeException {
        Preconditions.notNull(paramDto, "paramDto must not be null");
        Long userId = paramDto.getUserId();
        LocalDateTime requestTime = paramDto.getTime();
        ModuleDto data = paramDto.getModuleDto();
        List<ListItem> contents =
            monthlyFreeItemRepository.findRand5ByUserIdAndRequestTime(userId, requestTime).stream()
                .map(MonthlyFreeTicketDto::from)
                .map(listItemAggregateConverter::convert)
                .collect(Collectors.toList());

        return MonthlyFreeTicketResponse.of(
            data.getUpperModuleTitle(), data.getLowerModuleTitle(), contents
        );
    }
}
