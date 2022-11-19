package com.kilometer.domain.homeModules.modules;

import com.kilometer.domain.homeModules.ModuleParamDto;
import com.kilometer.domain.homeModules.enumType.ModuleType;

public interface ModuleHandler {

    boolean supports(ModuleType moduleType);

    Object generator(ModuleParamDto paramDto) throws RuntimeException;
}
