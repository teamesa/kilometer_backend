package com.kilometer.domain.homeModules;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HomeApiResponse {
    private List<ModuleDto> modules;

    public static HomeApiResponse from(List<ModuleDto> modules) {
        return new HomeApiResponse(modules);
    }
}
