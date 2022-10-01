package com.kilometer.domain.homeModules.modules.keyVisual;

import com.kilometer.domain.homeModules.modules.keyVisual.dto.KeyVisualResponse;
import com.kilometer.domain.homeModules.modules.keyVisual.dto.KeyVisualUpdateResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KeyVisualService {

    private final KeyVisualRepository keyVisualRepository;

    @Transactional
    public void updateKeyVisual(List<KeyVisualUpdateResponse> keyVisualList,
        String createdAccount) {
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

    @Transactional(readOnly = true)
    public List<KeyVisualResponse> findAllByKeyVisual() {
        return keyVisualRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
            .map(KeyVisual::makeResponse)
            .collect(Collectors.toList());
    }
}
