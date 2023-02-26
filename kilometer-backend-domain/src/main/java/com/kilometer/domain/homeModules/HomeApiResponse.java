package com.kilometer.domain.homeModules;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HomeApiResponse {

    private List<ModuleResponseDto<?>> modules;

    public static HomeApiResponse from(List<ModuleResponseDto<?>> modules) {
        return new HomeApiResponse(modules);
    }
}
