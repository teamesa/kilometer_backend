package com.kilometer.domain.homeModules.modules;

import com.kilometer.domain.homeModules.enums.ModuleType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.pool.TypePool.Resolution.Illegal;
import org.flywaydb.core.internal.license.FlywayTeamsUpgradeMessage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ModuleHandlerAdapter {

    private final List<ModuleHandler> handlers;

    public ModuleHandler getHandlerAdapter(ModuleType type) {
        return handlers.stream().filter(handler -> handler.supports(type))
            .findFirst().orElseThrow(() -> new IllegalArgumentException("Handler not exists"));

    }
}
