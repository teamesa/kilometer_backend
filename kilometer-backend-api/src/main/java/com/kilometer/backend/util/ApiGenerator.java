package com.kilometer.backend.util;

import com.kilometer.domain.homeModules.HomeApiResponse;
import com.kilometer.domain.homeModules.ModuleResponseDto;
import java.util.List;
import java.util.stream.Collectors;

public class ApiGenerator {

    public static HomeApiResponse generatorGetHomeMoulesApi(HomeApiResponse homeApiResponse) {
        List<ModuleResponseDto<Object>> moduleResponseDtos = homeApiResponse.getModules()
                .stream()
                .map(HomeModuleApiFactory::from)
                .collect(Collectors.toList());
        return HomeApiResponse.from(moduleResponseDtos);
    }
}
