package com.kilometer.domain.homeModules.modules;

import com.kilometer.domain.homeModules.enumType.ModuleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ModuleHandlerAdapter {

    private final List<ModuleHandler> handlers;

    public ModuleHandler getHandlerAdapter(ModuleType moduleType) {
        return handlers.stream().filter(handler -> handler.supports(moduleType))
            .findFirst().orElseThrow(() -> new IllegalArgumentException("Handler not exists"));

    }
}
