package com.kilometer.domain.homeModules;

import com.kilometer.domain.homeModules.dto.ModuleUpdateRequest;
import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.item.ItemService;
import com.kilometer.domain.item.dto.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ModuleValidator {

    private static final String FREE_TICKET_EXTRA_DATA_IS_EMPTY = "프리 티켓의 Extra Data는 비어있어야 합니다.";
    private static final String KEY_VISUAL_EXTRA_DATA_IS_EMPTY = "키비주얼의 Extra Data는 비어있어야 합니다.";
    private static final String ITEM_ID_EMPTY = "전시글 모듈의 ITEM id를 입력해주세요.";
    private static final String ITEM_ID_ERROR = "유효한 ITEM id가 아닙니다. id : ";
    private static final String ITEM_INTRODUCE_ERROR = "해당 ITEM id의 소개글이 없습니다. id : ";
    private static final String ITEM_DETAIL_IMAGE_URLS_ERROR = "해당 ITEM id의 소개 이미지가 없습니다. id : ";
    private static final String EXPOSURE_ORDER_NUMBER_DUPLICATION = "해당 순서가 중복됩니다. : ";
    private static final String EXPOSURE_ORDER_NUMBER_IS_EMPTY = "순서가 비어있습니다.";
    private static final String MODULE_NAME_IS_EMPTY = "모듈이름을 선택해주세요.";
    private static final String ITEM_NOT_EXHIBIT_ERROR = "해당 ITEM id은 현재 미전시 되있습니다. id : ";

    private final ItemService itemService;

    public List<ModuleUpdateRequest> filterByEmptyAndNull(List<ModuleUpdateRequest> modules) {
        return modules.stream()
                .filter(ModuleUpdateRequest::isNotEmpty)
                .filter(ModuleUpdateRequest::isNotNull)
                .collect(Collectors.toList());
    }

    public List<String> validateModule(List<ModuleUpdateRequest> modules) {
        List<String> errors = new ArrayList<>();

        validationDuplicationExposureOrderNumber(modules, errors);
        validationEmptyExposureOrderNumber(modules, errors);
        validationEmptyModuleName(modules, errors);
        validationExtraDataSwipeItem(modules, errors);
        validationExtraDataKeyVisual(modules, errors);
        validationExtraDataMonthlyFreeTicket(modules, errors);

        return errors;
    }

    private void validationExtraDataMonthlyFreeTicket(List<ModuleUpdateRequest> modules, List<String> errors) {
        boolean isNotNull = modules.stream()
                .filter(module -> ModuleType.MONTHLY_FREE_ITEM.equals(module.getModuleName()))
                .map(ModuleUpdateRequest::getExtraData)
                .anyMatch(StringUtils::hasText);

        if (isNotNull) {
            errors.add(FREE_TICKET_EXTRA_DATA_IS_EMPTY);
        }
    }

    private void validationExtraDataKeyVisual(List<ModuleUpdateRequest> modules, List<String> errors) {
        boolean isNotNull = modules.stream()
                .filter(module -> ModuleType.KEY_VISUAL.equals(module.getModuleName()))
                .map(ModuleUpdateRequest::getExtraData)
                .anyMatch(StringUtils::hasText);

        if (isNotNull) {
            errors.add(KEY_VISUAL_EXTRA_DATA_IS_EMPTY);
        }
    }

    private void validationExtraDataSwipeItem(List<ModuleUpdateRequest> modules, List<String> errors) {
        boolean isNotNull = modules.stream()
                .filter(module -> ModuleType.SWIPE_ITEM.equals(module.getModuleName()))
                .map(ModuleUpdateRequest::getExtraData)
                .anyMatch(String::isBlank);

        if (isNotNull) {
            errors.add(ITEM_ID_EMPTY);
            return;
        }

        modules.stream()
                .filter(module -> ModuleType.SWIPE_ITEM.equals(module.getModuleName()))
                .map(ModuleUpdateRequest::getExtraData)
                .map(Long::parseLong)
                .forEach(itemId -> {
                    Optional<ItemResponse> targetItem = itemService.findOne(itemId);

                    if (targetItem.isEmpty()) {
                        errors.add(ITEM_ID_ERROR + itemId);
                        return;
                    }

                    ItemResponse item = targetItem.get();

                    if (!StringUtils.hasText(item.getIntroduce())) {
                        errors.add(ITEM_INTRODUCE_ERROR + item.getId());
                    }

                    if (item.getDetailImageUrls().isEmpty()) {
                        errors.add(ITEM_DETAIL_IMAGE_URLS_ERROR + item.getId());
                    }

                    if (item.getDetailImageUrls().isEmpty()) {
                        errors.add(ITEM_NOT_EXHIBIT_ERROR + item.getId());
                    }
                });
    }

    private void validationDuplicationExposureOrderNumber(
            List<ModuleUpdateRequest> modules, List<String> errors) {
        List<Integer> exposureOrderNumberList = modules.stream()
                .map(ModuleUpdateRequest::getExposureOrderNumber)
                .collect(Collectors.toList());

        HashSet<Integer> set = new HashSet<>();
        exposureOrderNumberList
                .forEach(exposureOrderNumber -> {
                    if (exposureOrderNumberList.indexOf(exposureOrderNumber)
                            != exposureOrderNumberList.lastIndexOf(exposureOrderNumber)) {
                        set.add(exposureOrderNumber);
                    }
                });

        if (!set.isEmpty()) {
            errors.add(EXPOSURE_ORDER_NUMBER_DUPLICATION + set);
        }
    }

    private void validationEmptyExposureOrderNumber(List<ModuleUpdateRequest> modules, List<String> errors) {
        boolean isNull = modules.stream()
                .map(ModuleUpdateRequest::getExposureOrderNumber)
                .anyMatch(Objects::isNull);

        if (isNull) {
            errors.add(EXPOSURE_ORDER_NUMBER_IS_EMPTY);
        }
    }

    private void validationEmptyModuleName(List<ModuleUpdateRequest> modules, List<String> errors) {
        boolean isNull = modules.stream()
                .map(ModuleUpdateRequest::getModuleName)
                .anyMatch(ModuleType.EMPTY::equals);

        if (isNull) {
            errors.add(MODULE_NAME_IS_EMPTY);
        }
    }
}
