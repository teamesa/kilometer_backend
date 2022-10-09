package com.kilometer.domain.homeModules;

import com.kilometer.domain.homeModules.modules.Module;
import com.kilometer.domain.homeModules.modules.ModuleHandlerAdapter;
import com.kilometer.domain.homeModules.modules.ModuleRepository;
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

    public HomeApiResponse getHomeModules() {
        List<Module> modules = moduleRepository.findAll();
        List<ModuleResponseDto> result = new ArrayList<>();
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
