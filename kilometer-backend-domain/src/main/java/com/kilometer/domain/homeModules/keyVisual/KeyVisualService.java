package com.kilometer.domain.homeModules.keyVisual;

import com.kilometer.domain.homeModules.keyVisual.dto.KeyVisualDataDto;
import com.kilometer.domain.homeModules.keyVisual.dto.KeyVisualResponse;
import com.kilometer.domain.homeModules.keyVisual.dto.KeyVisualUpdateResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KeyVisualService {

    private final KeyVisualRepository keyVisualRepository;

    public List<KeyVisualResponse> findAllByKeyVisual() {
        return keyVisualRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
            .map(KeyVisual::makeResponse)
            .collect(Collectors.toList());
    }

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

    public List<KeyVisualDataDto> generate() {
        List<KeyVisual> keyVisuals = keyVisualRepository.findAllOrderByIdAtAsc();
        return keyVisuals.stream()
            .map(KeyVisualDataDto::from)
            .collect(Collectors.toList());
    }
}
