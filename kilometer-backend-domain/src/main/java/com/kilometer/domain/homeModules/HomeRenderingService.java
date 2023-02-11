package com.kilometer.domain.homeModules;

import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.homeModules.modules.ModuleHandlerAdapter;
import com.kilometer.domain.homeModules.modules.ModuleRepository;
import com.kilometer.domain.homeModules.modules.dto.ModuleDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeRenderingService {

    private final ModuleHandlerAdapter adapter;
    private final ModuleRepository moduleRepository;
    private final ModuleParamGenerator moduleParamGenerator;

    public ModuleResponseDto<Object> getKeyVisual(Long userId) {
        ModuleType type = ModuleType.KEY_VISUAL;
        ModuleParamDto paramDto = moduleParamGenerator.from(userId, null);
        try {
            return ModuleResponseDto.of(type, 0, adapter.getHandlerAdapter(type).generator(paramDto));
        } catch (Exception e) {
            // TODO KeyVisual Exception handling
            throw new RuntimeException(e);
        }
    }

    public HomeApiResponse getHomeModules(Long userId) {
        List<ModuleParamDto> modules = moduleRepository.findAllByOrderByExposureOrderNumber().stream()
            .map(module -> moduleParamGenerator.from(userId, ModuleDto.from(module)))
            .collect(Collectors.toList());

        List<ModuleResponseDto<Object>> result = new ArrayList<>();
        int indexCount = 0;
        for (ModuleParamDto moduleParamDto : modules) {
            try {
                ModuleDto moduleDto = moduleParamDto.getModuleDto();
                Object data = adapter.getHandlerAdapter(moduleDto.getModuleName())
                        .generator(moduleParamDto)
                        .orElseThrow(IllegalArgumentException::new);
                result.add(ModuleResponseDto.of(moduleDto.getModuleName(), indexCount, data));
                indexCount++;
            } catch (Exception e) {
                log.error("Home api catch exception", e);
            }
        }
        return HomeApiResponse.from(result);
    }
}
