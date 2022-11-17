package com.kilometer.domain.homeModules;

import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.homeModules.modules.Module;
import com.kilometer.domain.homeModules.modules.ModuleHandler;
import com.kilometer.domain.homeModules.modules.ModuleHandlerAdapter;
import com.kilometer.domain.homeModules.modules.ModuleRepository;
import java.util.Optional;
import javax.swing.text.html.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
        int indexCount = 0;
        for (Module module : modules) {
            try {
                result.add(
                    ModuleResponseDto.of(
                        module.getModuleName(),
                        indexCount,
                        adapter.getHandlerAdapter(module.getModuleName())
                            .generator(module.getExtraData())
                    )
                );
                indexCount++;
            } catch (Exception e) {
                log.error("Home api catch exception", e);
            }
        }
        return HomeApiResponse.from(result);
    }
}
