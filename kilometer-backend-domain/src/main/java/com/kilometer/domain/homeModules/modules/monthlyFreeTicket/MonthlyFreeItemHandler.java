package com.kilometer.domain.homeModules.modules.monthlyFreeTicket;

import com.google.common.base.Preconditions;
import com.kilometer.domain.homeModules.ModuleParamDto;
import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.homeModules.modules.ModuleHandler;
import com.kilometer.domain.homeModules.modules.dto.ModuleDto;
import com.kilometer.domain.homeModules.modules.monthlyFreeTicket.dto.MonthlyFreeTicketResponse;
import com.kilometer.domain.item.ItemRepository;
import com.kilometer.domain.item.dto.MonthlyFreeTicketDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MonthlyFreeItemHandler implements ModuleHandler {

    private final ItemRepository itemRepository;

    @Override
    public boolean supports(ModuleType moduleType) {
        return moduleType == ModuleType.MONTHLY_FREE_ITEM;
    }

    @Override
    public Optional<Object> generator(ModuleParamDto paramDto) throws RuntimeException {
        Preconditions.checkNotNull(paramDto, "paramDto must not be null");
        LocalDateTime requestTime = paramDto.getTime();
        ModuleDto data = paramDto.getModuleDto();
        List<MonthlyFreeTicketDto> monthlyFreeTicketDtos = itemRepository.findTopRandomFiveMonthlyFreeTicket(
                requestTime.toLocalDate());

        if (monthlyFreeTicketDtos.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(MonthlyFreeTicketResponse.of(
                data.getUpperModuleTitle(),
                data.getLowerModuleTitle(),
                doesHeartedTicket(monthlyFreeTicketDtos, paramDto)
        ));
    }

    private List<MonthlyFreeTicketDto> doesHeartedTicket(final List<MonthlyFreeTicketDto> monthlyFreeTicketDtos,
                                                         ModuleParamDto moduleParamDto) {
        return monthlyFreeTicketDtos.stream()
                .map(content -> isAuthenticatedUser(content, moduleParamDto))
                .collect(Collectors.toList());
    }

    private MonthlyFreeTicketDto isAuthenticatedUser(final MonthlyFreeTicketDto monthlyFreeTicketDto,
                                                     final ModuleParamDto moduleParamDto) {
        if (monthlyFreeTicketDto.getUserId().equals(moduleParamDto.getUserId())) {
            return monthlyFreeTicketDto;
        }
        return MonthlyFreeTicketDto.builder()
                .itemId(monthlyFreeTicketDto.getItemId())
                .thumbnailImageUrl(monthlyFreeTicketDto.getThumbnailImageUrl())
                .title(monthlyFreeTicketDto.getTitle())
                .exhibitionType(monthlyFreeTicketDto.getExhibitionType())
                .exposureType(monthlyFreeTicketDto.getExposureType())
                .feeType(monthlyFreeTicketDto.getFeeType())
                .pickCount(monthlyFreeTicketDto.getPickCount())
                .isHearted(false)
                .userId(monthlyFreeTicketDto.getUserId())
                .archiveCount(monthlyFreeTicketDto.getArchiveCount())
                .grade(monthlyFreeTicketDto.getGrade())
                .build();
    }

}
