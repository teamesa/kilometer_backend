package com.kilometer.backend.util;

import com.kilometer.backend.controller.dto.module.realtimearchive.ArchivesResponse;
import com.kilometer.domain.homeModules.ModuleResponseDto;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class HomeModuleApiFactory {

    public static Map<String, Function<ModuleResponseDto, Object>> CACHE;

    static {
        CACHE = new HashMap<>();
        CACHE.put("REAL_TIME_ARCHIVE", ArchivesResponse::from);
    }

    public static ModuleResponseDto<Object> from(ModuleResponseDto<Object> moduleResponseDto) {
        Object data = CACHE.getOrDefault(moduleResponseDto.getModuleName(), ModuleResponseDto::getData)
                .apply(moduleResponseDto);
        return new ModuleResponseDto<>(moduleResponseDto.getModuleName(), moduleResponseDto.getIndex(), data);
    }
}
