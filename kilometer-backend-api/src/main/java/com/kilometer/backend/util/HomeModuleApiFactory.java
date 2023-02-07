package com.kilometer.backend.util;

import com.kilometer.backend.controller.dto.module.realtimearchive.ArchivesResponse;
import com.kilometer.domain.homeModules.ModuleResponseDto;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class HomeModuleApiFactory {

    public final Map<String, Function<ModuleResponseDto, Object>> moduleApiMapper;

    public HomeModuleApiFactory() {
        moduleApiMapper = new HashMap<>();
        moduleApiMapper.put("REAL_TIME_ARCHIVE", ArchivesResponse::from);
    }

    public ModuleResponseDto<Object> from(ModuleResponseDto<Object> moduleResponseDto) {
        Object data = moduleApiMapper.getOrDefault(moduleResponseDto.getModuleName(), ModuleResponseDto::getData)
                .apply(moduleResponseDto);
        return new ModuleResponseDto<>(moduleResponseDto.getModuleName(), moduleResponseDto.getIndex(), data);
    }
}
