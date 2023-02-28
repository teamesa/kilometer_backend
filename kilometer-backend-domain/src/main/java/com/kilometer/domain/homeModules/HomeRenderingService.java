package com.kilometer.domain.homeModules;

import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.homeModules.modules.ModuleHandlerAdapter;
import com.kilometer.domain.homeModules.modules.ModuleRepository;
import com.kilometer.domain.homeModules.modules.dto.ModuleDto;
import com.kilometer.domain.homeModules.modules.keyVisual.dto.KeyVisualApiResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomeRenderingService {

    private final ModuleHandlerAdapter adapter;
    private final ModuleRepository moduleRepository;
    private final ModuleParamGenerator moduleParamGenerator;

    public ModuleResponseDto<KeyVisualApiResponse> getKeyVisual(Long userId) {
        ModuleType type = ModuleType.KEY_VISUAL;
        ModuleParamDto paramDto = moduleParamGenerator.from(userId, null);
        try {
            var keyVisualApiResponse = adapter.getHandlerAdapter(type)
                    .generator(paramDto)
                    .orElseThrow(IllegalAccessError::new);
            return ModuleResponseDto.of(type, 0, (KeyVisualApiResponse) keyVisualApiResponse);
        } catch (Exception e) {
            // TODO KeyVisual Exception handling
            throw new RuntimeException(e);
        }
    }

    public HomeApiResponse getHomeModules(Long userId) {
        List<ModuleParamDto> modules = moduleRepository.findAllByOrderByExposureOrderNumber().stream()
            .map(module -> moduleParamGenerator.from(userId, ModuleDto.from(module)))
            .collect(Collectors.toList());

        List<ModuleResponseDto<?>> result = new ArrayList<>();
        for (ModuleParamDto moduleParamDto : modules) {
            try {
                ModuleDto moduleDto = moduleParamDto.getModuleDto();
                adapter.getHandlerAdapter(moduleDto.getModuleName())
                        .generator(moduleParamDto)
                        .ifPresent(data -> result.add(ModuleResponseDto.of(moduleDto.getModuleName(), moduleDto.getExposureOrderNumber(), data)));
            } catch (Exception e) {
                log.error("Home api catch exception", e);
            }
        }
        result.sort(Comparator.comparingInt(ModuleResponseDto::getIndex));
        return HomeApiResponse.from(result);
    }
}
