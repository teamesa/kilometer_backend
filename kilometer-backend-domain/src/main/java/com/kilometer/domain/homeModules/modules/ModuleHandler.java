package com.kilometer.domain.homeModules.modules;

import com.kilometer.domain.homeModules.ModuleParamDto;
import com.kilometer.domain.homeModules.enumType.ModuleType;
import java.util.Optional;

public interface ModuleHandler {

    boolean supports(ModuleType moduleType);

    Optional<?> generator(ModuleParamDto paramDto);
}
