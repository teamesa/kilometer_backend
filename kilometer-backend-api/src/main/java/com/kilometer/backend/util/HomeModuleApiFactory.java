package com.kilometer.backend.util;

import com.kilometer.backend.controller.dto.module.realtimearchive.ArchivesDto;
import com.kilometer.domain.homeModules.ModuleResponseDto;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class HomeModuleApiFactory {

    public static Map<String, Function<ModuleResponseDto, Object>> cache;

    static {
        cache = new HashMap<>();
        cache.put("REAL_TIME_ARCHIVE", ArchivesDto::from);
    }

    public static ModuleResponseDto<Object> from(ModuleResponseDto<Object> moduleResponseDto) {
        Object data = cache.getOrDefault(moduleResponseDto.getModuleName(), ModuleResponseDto::getData)
                .apply(moduleResponseDto);
        return new ModuleResponseDto<>(moduleResponseDto.getModuleName(), moduleResponseDto.getIndex(), data);
    }
}
