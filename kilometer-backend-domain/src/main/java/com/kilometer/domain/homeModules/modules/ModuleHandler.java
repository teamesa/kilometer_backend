package com.kilometer.domain.homeModules.modules;

import com.kilometer.domain.homeModules.enums.ModuleType;

public interface ModuleHandler {

    boolean supports(ModuleType moduleType);

    Object generator(String data);
}