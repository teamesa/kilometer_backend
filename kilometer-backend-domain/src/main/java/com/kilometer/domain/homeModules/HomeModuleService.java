package com.kilometer.domain.homeModules;

import com.kilometer.domain.homeModules.enums.ModuleType;
import com.kilometer.domain.homeModules.modules.Module;
import com.kilometer.domain.homeModules.modules.ModuleHandlerAdapter;
import com.kilometer.domain.homeModules.modules.ModuleRepository;
import com.kilometer.domain.homeModules.modules.keyVisual.KeyVisualHandler;
import com.kilometer.domain.homeModules.modules.keyVisual.dto.KeyVisualDataDto;
import com.kilometer.domain.homeModules.modules.keyVisual.dto.KeyVisualResponse;
import com.kilometer.domain.homeModules.modules.keyVisual.dto.KeyVisualUpdateResponse;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeModuleService {

    private final ModuleHandlerAdapter adapter;
    private final ModuleRepository moduleRepository;

    public HomeApiResponse getHomeModules() {
        List<Module> modules = moduleRepository.findAll();
        List<ModuleDto> result = new ArrayList<>();
        for(int i=0;i<modules.size();i++) {
            Module module = modules.get(i);
            result.add(ModuleDto.of(module.getModuleName().getFrontName(), i,
                adapter.getHandlerAdapter(modules.get(i).getModuleName()).generator(module.getExtraData())
            ));
        }
        return HomeApiResponse.from(result);
    }
}
