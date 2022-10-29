package com.kilometer.domain.homeModules;

import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.homeModules.modules.Module;
import com.kilometer.domain.homeModules.modules.ModuleHandlerAdapter;
import com.kilometer.domain.homeModules.modules.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeRenderingService {

    private final ModuleHandlerAdapter adapter;
    private final ModuleRepository moduleRepository;

    public ModuleResponseDto<Object> getKeyVisual() {
        ModuleType type = ModuleType.KEY_VISUAL;
        return ModuleResponseDto.of(type, 0, adapter.getHandlerAdapter(type).generator(null));
    }

    public HomeApiResponse getHomeModules() {
        List<Module> modules = moduleRepository.findAll();
        List<ModuleResponseDto<Object>> result = new ArrayList<>();
        for (int i = 0; i < modules.size(); i++) {
            Module module = modules.get(i);
            result.add(
                ModuleResponseDto.of(module.getModuleName(), i,
                    adapter.getHandlerAdapter(module.getModuleName())
                        .generator(module.getExtraData())
                ));
        }
        return HomeApiResponse.from(result);
    }
}
