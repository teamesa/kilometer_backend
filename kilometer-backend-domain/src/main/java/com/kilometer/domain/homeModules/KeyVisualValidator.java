package com.kilometer.domain.homeModules;

import com.kilometer.domain.homeModules.modules.keyVisual.dto.KeyVisualUpdateResponse;
import org.mockito.internal.util.collections.Sets;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class KeyVisualValidator {

    private static final Set<Long> ID_SET = Sets.newSet(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
    private static final int KEY_VISUAL_MIN_COUNT = 3;
    private static final int KEY_VISUAL_ID_COUNT = 10;
    private static final String KEY_VISUAL_ID_DUPLICATION_ERROR = "ID는 중복 될 수 없으며 해당 ID가 없습니다. id: ";
    private static final String KEY_VISUAL_LACK = "3개 이상 입력해주세요.";
    private static final String KEY_VISUAL_ID_RANGE_OUT = "ID는 1부터 10까지 할 수 있습니다. id: ";

    public List<String> validateKeyVisual(List<KeyVisualUpdateResponse> keyVisualList) {
        List<String> errors = new ArrayList<>();

        validationListCount(keyVisualList, errors);
        validationKeyVisualIdBySet(keyVisualList, errors);

        return errors;
    }

    private void validationKeyVisualIdBySet(List<KeyVisualUpdateResponse> keyVisualList, List<String> errors) {
        Set<Long> inputIds = keyVisualList.stream().map(KeyVisualUpdateResponse::getId).collect(Collectors.toSet());

        if (inputIds.size() != KEY_VISUAL_ID_COUNT) {
            errors.add(KEY_VISUAL_ID_DUPLICATION_ERROR + getSubtractionString(ID_SET, inputIds));
        } else if (!inputIds.equals(ID_SET)) {
            errors.add(KEY_VISUAL_ID_RANGE_OUT + getSubtractionString(inputIds, ID_SET));
        }
    }

    private String getSubtractionString(Set<Long> target, Set<Long> sub) {
        Set<Long> errorIds = new HashSet<>(target);
        errorIds.removeAll(sub);
        List<String> errorIdList = errorIds.stream().map(String::valueOf).collect(Collectors.toList());
        return StringUtils.collectionToCommaDelimitedString(errorIdList);
    }

    private void validationListCount(List<KeyVisualUpdateResponse> keyVisualList, List<String> errors) {
        long count = keyVisualList.stream()
                .filter(KeyVisualUpdateResponse::isNotNull)
                .count();

        if (count < KEY_VISUAL_MIN_COUNT) {
            errors.add(KEY_VISUAL_LACK);
        }
    }
}
