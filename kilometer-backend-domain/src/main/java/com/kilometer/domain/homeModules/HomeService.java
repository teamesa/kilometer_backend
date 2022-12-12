package com.kilometer.domain.homeModules;

import com.kilometer.domain.backOfficeAccount.BackOfficeAccount;
import com.kilometer.domain.backOfficeAccount.BackOfficeAccountService;
import com.kilometer.domain.homeModules.dto.ModuleResponse;
import com.kilometer.domain.homeModules.dto.ModuleTypeDto;
import com.kilometer.domain.homeModules.dto.ModuleUpdateRequest;
import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.homeModules.modules.Module;
import com.kilometer.domain.homeModules.modules.ModuleRepository;
import com.kilometer.domain.homeModules.modules.keyVisual.KeyVisual;
import com.kilometer.domain.homeModules.modules.keyVisual.KeyVisualRepository;
import com.kilometer.domain.homeModules.modules.keyVisual.dto.KeyVisualResponse;
import com.kilometer.domain.homeModules.modules.keyVisual.dto.KeyVisualUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {

    private final KeyVisualRepository keyVisualRepository;
    private final ModuleRepository moduleRepository;
    private final BackOfficeAccountService backOfficeAccountService;
    private final ModuleValidator moduleValidator;
    private final KeyVisualValidator keyVisualValidator;

    public List<KeyVisualResponse> findAllByKeyVisual() {
        return keyVisualRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(KeyVisual::makeResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<String> updateKeyVisualAfterValidate(List<KeyVisualUpdateResponse> keyVisualList, String createdAccount) {
        List<String> errors = validateKeyVisual(keyVisualList);

        if (errors.isEmpty()) {
            updateKeyVisual(keyVisualList, createdAccount);
        }

        return errors;
    }

    private List<String> validateKeyVisual(List<KeyVisualUpdateResponse> keyVisualList) {
        return keyVisualValidator.validateKeyVisual(keyVisualList);
    }

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

    @Transactional
    public List<String> updateModuleAfterValidate(List<ModuleUpdateRequest> moduleList, String createdAccount) {
        List<ModuleUpdateRequest> modules = moduleFilter(moduleList);
        List<String> errors = validateModule(modules);

        if (errors.isEmpty()) {
            updateModule(modules, createdAccount);
        }

        return errors;
    }

    private List<ModuleUpdateRequest> moduleFilter(List<ModuleUpdateRequest> moduleList) {
        return moduleValidator.filterByEmptyAndNull(moduleList);
    }

    private List<String> validateModule(List<ModuleUpdateRequest> moduleList) {
        return moduleValidator.validateModule(moduleList);
    }

    private void updateModule(List<ModuleUpdateRequest> moduleList, String createdAccount) {
        BackOfficeAccount account = backOfficeAccountService.findByUsername(createdAccount);

        List<Module> saveAndUpdateModules = moduleList.stream()
                .map(module -> module.makeModule(account))
                .collect(Collectors.toList());

        moduleRepository.saveAll(saveAndUpdateModules);
    }

    @Transactional
    public void deleteModule(List<Long> moduleIdList) {
        moduleIdList.forEach(moduleRepository::deleteById);
    }

    public List<ModuleTypeDto> makeModuleType() {
        return Stream.of(ModuleType.values())
                .map(value -> ModuleTypeDto.of(value, value.getDescription(), value.getFrontName()))
                .collect(Collectors.toList());
    }
}
