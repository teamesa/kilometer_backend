package com.kilometer.domain.homeModules;

import com.kilometer.domain.homeModules.enums.ModuleType;
import com.kilometer.domain.homeModules.modules.Module;
import com.kilometer.domain.homeModules.modules.ModuleHandlerAdapter;
import com.kilometer.domain.homeModules.modules.ModuleRepository;
import com.kilometer.domain.homeModules.modules.dto.ModuleDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeRenderingService {

    private final ModuleHandlerAdapter adapter;
    private final ModuleRepository moduleRepository;

    public ModuleResponseDto<Object> getKeyVisual(Long userId) {
        ModuleType type = ModuleType.KEY_VISUAL;
        ModuleParamDto paramDto = ModuleParamGenerator.from(userId, null);
        return ModuleResponseDto.of(type, 0, adapter.getHandlerAdapter(type).generator(paramDto));
    }

    public HomeApiResponse getHomeModules(Long userId) {
        List<Module> modules = moduleRepository.findAll();
        List<ModuleResponseDto<Object>> result = new ArrayList<>();
        for (int i = 0; i < modules.size(); i++) {
            Module module = modules.get(i);
            ModuleDto moduleDto = ModuleDto.from(module);
            ModuleParamDto paramDto = ModuleParamGenerator.from(userId, moduleDto);
            result.add(
                ModuleResponseDto.of(module.getModuleName(), i,
                    adapter.getHandlerAdapter(module.getModuleName()).generator(paramDto)
                ));
        }
        return HomeApiResponse.from(result);
    }
}
