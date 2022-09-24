package com.kilometer.domain.homeModules;

import com.kilometer.domain.homeModules.keyVisual.KeyVisual;
import com.kilometer.domain.homeModules.keyVisual.KeyVisualRepository;
import com.kilometer.domain.homeModules.keyVisual.KeyVisualService;
import com.kilometer.domain.homeModules.keyVisual.dto.KeyVisualDataDto;
import com.kilometer.domain.homeModules.keyVisual.dto.KeyVisualResponse;
import com.kilometer.domain.homeModules.keyVisual.dto.KeyVisualUpdateResponse;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeModuleService {

    private static final String KEY_VISUAL_MODULE_NAME = "keyVisual";
    private final KeyVisualService keyVisualService;

    public HomeApiResponse home() {
        List<ModuleDto> modules = new ArrayList<>();
        modules.add(new ModuleDto<>(KEY_VISUAL_MODULE_NAME, 0, keyVisual()));
        return HomeApiResponse.from(modules);
    }

    private List<KeyVisualDataDto> keyVisual() {
        return keyVisualService.generate();
    }

    public List<KeyVisualResponse> findAllByKeyVisual() {
        return keyVisualService.findAllByKeyVisual();
    }

    @Transactional
    public void updateKeyVisual(List<KeyVisualUpdateResponse> keyVisualList, String createdAccount) {
        keyVisualService.updateKeyVisual(keyVisualList,createdAccount);
    }
}
