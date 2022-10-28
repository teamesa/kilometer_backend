package com.kilometer.domain.homeModules;

import com.kilometer.domain.homeModules.dto.ModuleUpdateRequest;
import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.item.ItemService;
import com.kilometer.domain.item.dto.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ModuleValidator {

    private final ItemService itemService;

    public List<ModuleUpdateRequest> filterByEmptyAndNull(List<ModuleUpdateRequest> modules) {
        return modules.stream()
                .filter(ModuleUpdateRequest::isNotEmpty)
                .filter(ModuleUpdateRequest::isNotNull)
                .collect(Collectors.toList());
    }

    public void validateModule(List<ModuleUpdateRequest> modules, BindingResult bindingResult) {
        validationDuplicationExposureOrderNumber(modules, bindingResult);
        validationEmptyExposureOrderNumber(modules, bindingResult);
        validationEmptyModuleName(modules, bindingResult);
        validationExtraDataSwipeItem(modules, bindingResult);
        validationExtraDataKeyVisual(modules, bindingResult);
        validationExtraDataMonthlyFreeItem(modules, bindingResult);
    }

    private void validationExtraDataMonthlyFreeItem(List<ModuleUpdateRequest> modules, BindingResult bindingResult) {
        boolean isNotNull = modules.stream()
                .filter(module -> ModuleType.MONTHLY_FREE_ITEM.equals(module.getModuleName()))
                .map(ModuleUpdateRequest::getExtraData)
                .anyMatch(StringUtils::hasText);

        if (isNotNull) {
            bindingResult.reject("checkedExtraDataMonthlyFreeItem");
        }
    }

    private void validationExtraDataKeyVisual(List<ModuleUpdateRequest> modules, BindingResult bindingResult) {
        boolean isNotNull = modules.stream()
                .filter(module -> ModuleType.KEY_VISUAL.equals(module.getModuleName()))
                .map(ModuleUpdateRequest::getExtraData)
                .anyMatch(StringUtils::hasText);

        if (isNotNull) {
            bindingResult.reject("checkedExtraDataKeyVisual");
        }
    }

    private void validationExtraDataSwipeItem(List<ModuleUpdateRequest> modules, BindingResult bindingResult) {
        try {
            List<ItemResponse> items = modules.stream()
                    .filter(module -> ModuleType.SWIPE_ITEM.equals(module.getModuleName()))
                    .map(module -> itemService.findOne(Long.valueOf(module.getExtraData())))
                    .collect(Collectors.toList());

            boolean isNull = items.stream()
                    .map(ItemResponse::getIntroduce)
                    .anyMatch(Predicate.not(StringUtils::hasText));

            if (isNull) {
                bindingResult.reject("checkedExtraDataSwipeItemIntroduce");
            }

            List<Long> detailImageUrlsCount = items.stream()
                    .map(item -> (long) item.getDetailImageUrls().size())
                    .collect(Collectors.toList());

            if (detailImageUrlsCount.stream().anyMatch(count -> count == 0)) {
                bindingResult.reject("checkedExtraDataSwipeItemDetailImageUrl");
            }
        } catch (IllegalArgumentException e) {
            bindingResult.reject("checkedExtraDataSwipeItem");
        }
    }

    private void validationDuplicationExposureOrderNumber(
            List<ModuleUpdateRequest> modules, BindingResult bindingResult) {
        List<Integer> exposureOrderNumberList = modules.stream()
                .map(ModuleUpdateRequest::getExposureOrderNumber)
                .collect(Collectors.toList());

        if (exposureOrderNumberList.size() != exposureOrderNumberList.stream().distinct().count()) {
            bindingResult.reject("checkedSequenceDistinct");
        }
    }

    private void validationEmptyExposureOrderNumber(List<ModuleUpdateRequest> modules, BindingResult bindingResult) {
        boolean isNull = modules.stream()
                .map(ModuleUpdateRequest::getExposureOrderNumber)
                .anyMatch(Objects::isNull);

        if (isNull) {
            bindingResult.reject("checkedSequenceEmpty");
        }
    }

    private void validationEmptyModuleName(List<ModuleUpdateRequest> modules, BindingResult bindingResult) {
        boolean isNull = modules.stream()
                .map(ModuleUpdateRequest::getModuleName)
                .anyMatch(ModuleType.EMPTY::equals);

        if (isNull) {
            bindingResult.reject("checkedModuleNameEmpty");
        }
    }
}
