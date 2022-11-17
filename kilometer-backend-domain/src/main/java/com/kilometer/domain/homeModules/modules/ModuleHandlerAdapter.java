package com.kilometer.domain.homeModules.modules;

import com.kilometer.domain.homeModules.enumType.ModuleType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ModuleHandlerAdapter {

    private final List<ModuleHandler> handlers;

    public ModuleHandler getHandlerAdapter(ModuleType moduleType) {
        return handlers.stream()
            .filter(moduleHandler -> moduleHandler.supports(moduleType))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(moduleType + " Not supported"));
    }

}
