package com.kilometer.domain.homeModules;

import com.kilometer.domain.homeModules.modules.keyVisual.dto.KeyVisualUpdateResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyVisualValidator {

    private static final Integer KEY_VISUAL_MIN_COUNT = 3;
    private static final String KEY_VISUAL_LACK = "3개 이상 입력해주세요.";

    public List<String> validateKeyVisual(List<KeyVisualUpdateResponse> keyVisualList) {
        List<String> errors = new ArrayList<>();

        validationListCount(keyVisualList, errors);

        return errors;
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
