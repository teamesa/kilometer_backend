package com.kilometer.domain.homeModules;

import com.kilometer.domain.homeModules.dto.ModuleUpdateRequest;
import com.kilometer.domain.homeModules.enumType.ModuleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ModuleValidator {

    public List<ModuleUpdateRequest> validateModule(List<ModuleUpdateRequest> moduleList, BindingResult bindingResult) {
        List<ModuleUpdateRequest> modules = removeEmpty(moduleList);

        validationDuplicationExposureOrderNumber(modules, bindingResult);
        validationEmptyExposureOrderNumber(modules, bindingResult);
        validationEmptyModuleName(modules, bindingResult);

        return modules;
    }

    private List<ModuleUpdateRequest> removeEmpty(List<ModuleUpdateRequest> moduleList) {
        return moduleList.stream()
                .filter(ModuleUpdateRequest::isNotEmpty)
                .collect(Collectors.toList());
    }

    private void validationDuplicationExposureOrderNumber(
            List<ModuleUpdateRequest> modules, BindingResult bindingResult) {
        List<Integer> exposureOrderNumberList = modules.stream()
                .filter(ModuleUpdateRequest::isNotDelete)
                .map(ModuleUpdateRequest::getExposureOrderNumber)
                .collect(Collectors.toList());

        if (exposureOrderNumberList.size() != exposureOrderNumberList.stream().distinct().count()) {
            bindingResult.reject("checkedSequenceDistinct");
        }
    }

    private void validationEmptyExposureOrderNumber(List<ModuleUpdateRequest> modules, BindingResult bindingResult) {
        boolean isNull = modules.stream()
                .filter(ModuleUpdateRequest::isNotDelete)
                .map(ModuleUpdateRequest::getExposureOrderNumber)
                .anyMatch(Objects::isNull);

        if (isNull) {
            bindingResult.reject("checkedSequenceEmpty");
        }
    }

    private void validationEmptyModuleName(List<ModuleUpdateRequest> modules, BindingResult bindingResult) {
        boolean isNull = modules.stream()
                .filter(ModuleUpdateRequest::isNotDelete)
                .map(ModuleUpdateRequest::getModuleName)
                .anyMatch(ModuleType.EMPTY::equals);

        if (isNull) {
            bindingResult.reject("checkedModuleNameEmpty");
        }
    }
}
