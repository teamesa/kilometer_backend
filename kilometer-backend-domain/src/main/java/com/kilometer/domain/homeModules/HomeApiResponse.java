package com.kilometer.domain.homeModules;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HomeApiResponse {
    private List<ModuleResponseDto<Object>> modules;

    public static HomeApiResponse from(List<ModuleResponseDto<Object>> modules) {
        return new HomeApiResponse(modules);
    }
}
