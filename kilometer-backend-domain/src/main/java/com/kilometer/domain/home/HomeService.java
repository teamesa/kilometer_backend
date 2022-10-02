package com.kilometer.domain.home;

import com.kilometer.domain.backOfficeAccount.BackOfficeAccount;
import com.kilometer.domain.backOfficeAccount.BackOfficeAccountService;
import com.kilometer.domain.home.keyVisual.KeyVisual;
import com.kilometer.domain.home.keyVisual.KeyVisualRepository;
import com.kilometer.domain.home.keyVisual.dto.KeyVisualResponse;
import com.kilometer.domain.home.keyVisual.dto.KeyVisualUpdateResponse;
import com.kilometer.domain.homeModules.Module;
import com.kilometer.domain.homeModules.ModuleRepository;
import com.kilometer.domain.homeModules.dto.ModuleResponse;
import com.kilometer.domain.homeModules.dto.ModuleResponseList;
import com.kilometer.domain.homeModules.dto.ModuleUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeService {

    private final KeyVisualRepository keyVisualRepository;
    private final ModuleRepository moduleRepository;
    private final BackOfficeAccountService backOfficeAccountService;

    public List<KeyVisualResponse> findAllByKeyVisual() {
        return keyVisualRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(KeyVisual::makeResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateKeyVisual(List<KeyVisualUpdateResponse> keyVisualList, String createdAccount) {
        List<KeyVisual> keyVisuals = keyVisualList.stream()
                .map(keyVisualUpdateResponse -> {
                    KeyVisual keyVisual = keyVisualRepository.findById(keyVisualUpdateResponse.getId())
                            .orElseThrow(() -> new IllegalArgumentException(
                                    "KeyVisual이 존재하지 않습니다. id=" + keyVisualUpdateResponse.getId()));
                    keyVisual.update(keyVisualUpdateResponse, createdAccount);
                    return keyVisual;
                }).collect(Collectors.toList());
        keyVisualRepository.saveAll(keyVisuals);
    }

    public List<ModuleResponse> findAllByModule() {
        return moduleRepository.findAll(Sort.by(Sort.Direction.ASC, "exposureOrderNumber")).stream()
                .map(Module::makeResponse)
                .collect(Collectors.toList());
    }

    public ModuleResponseList findAllByUpdateModule() {
        return ModuleResponseList.builder()
                .moduleList(findAllByModule())
                .build();
    }

    @Transactional
    public void updateModule(List<ModuleUpdateRequest> moduleList, String createdAccount) {
        BackOfficeAccount account = backOfficeAccountService.findByUsername(createdAccount);

        List<Module> updateModules = deletedModules(moduleList, account);

        moduleRepository.saveAll(updateModules);
    }

    private List<Module> deletedModules(List<ModuleUpdateRequest> checkedModules, BackOfficeAccount account) {
        return checkedModules.stream()
                .filter(this::isNotDeleteModule)
                .map(moduleUpdateRequest -> moduleUpdateRequest.makeModule(account))
                .collect(Collectors.toList());
    }

    private boolean isNotDeleteModule(ModuleUpdateRequest moduleUpdateRequest) {
        boolean isNotDelete = moduleUpdateRequest.getExposureOrderNumber() != null
                || moduleUpdateRequest.getModuleName() != null
                || StringUtils.hasText(moduleUpdateRequest.getUpperModuleTitle())
                || StringUtils.hasText(moduleUpdateRequest.getLowerModuleTitle())
                || StringUtils.hasText(moduleUpdateRequest.getExtraData());
        if (!isNotDelete) {
            moduleRepository.deleteById(moduleUpdateRequest.getId());
        }
        return isNotDelete;
    }
}
