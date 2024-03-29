package com.kilometer.backend.util;

import com.kilometer.backend.controller.dto.module.monthlyfreeticket.MonthlyFreeTicketsResponse;
import com.kilometer.backend.controller.dto.module.realtimearchive.ArchivesResponse;
import com.kilometer.domain.homeModules.ModuleResponseDto;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class HomeModuleApiFactory {

    public final Map<String, Function<ModuleResponseDto<?>, ?>> moduleApiMapper;

    public HomeModuleApiFactory() {
        moduleApiMapper = new HashMap<>();
        moduleApiMapper.put("REAL_TIME_ARCHIVE", ArchivesResponse::from);
        moduleApiMapper.put("MONTHLY_FREE_ITEM", MonthlyFreeTicketsResponse::from);
    }

    public ModuleResponseDto<?> from(ModuleResponseDto<?> moduleResponseDto) {
        var data = moduleApiMapper.getOrDefault(moduleResponseDto.getModuleName(), ModuleResponseDto::getData)
                .apply(moduleResponseDto);
        return new ModuleResponseDto<>(moduleResponseDto.getModuleName(), moduleResponseDto.getIndex(), data);
    }
}
