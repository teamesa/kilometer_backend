package com.kilometer.backend.util;

import com.kilometer.domain.homeModules.HomeApiResponse;
import com.kilometer.domain.homeModules.ModuleResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiGenerator {

    private final HomeModuleApiFactory homeModuleApiFactory;

    public HomeApiResponse generatorGetHomeMoulesApi(HomeApiResponse homeApiResponse) {
        List<ModuleResponseDto<?>> moduleResponseDtos = homeApiResponse.getModules()
                .stream()
                .map(homeModuleApiFactory::from)
                .collect(Collectors.toList());
        return HomeApiResponse.from(moduleResponseDtos);
    }
}
