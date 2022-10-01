package com.kilometer.domain.homeModules.modules;

import com.kilometer.domain.homeModules.enums.ModuleType;
import org.springframework.stereotype.Component;

@Component
public interface ModuleHandler {

    boolean supports(ModuleType moduleType);

    Object generator(String data);
}
