package com.kilometer.domain.homeModules.modules.keyVisual;

import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.homeModules.modules.ModuleHandler;
import com.kilometer.domain.homeModules.modules.keyVisual.dto.KeyVisualApiResponse;
import com.kilometer.domain.homeModules.modules.keyVisual.dto.KeyVisualDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KeyVisualHandler implements ModuleHandler {

    private final KeyVisualRepository keyVisualRepository;

    @Override
    public boolean supports(ModuleType moduleType) {
        return moduleType == ModuleType.KEY_VISUAL;
    }

    @Override
    public Object generator(String data) {
        List<KeyVisual> keyVisuals = keyVisualRepository.findAllOrderByIdAtAsc();
        return KeyVisualApiResponse.from(
            keyVisuals.stream()
            .map(KeyVisualDataDto::from)
            .collect(Collectors.toList())
        );
    }
}
