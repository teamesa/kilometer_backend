package com.kilometer.domain.home;

import com.kilometer.domain.home.keyVisual.KeyVisual;
import com.kilometer.domain.home.keyVisual.KeyVisualRepository;
import com.kilometer.domain.home.keyVisual.dto.KeyVisualResponse;
import com.kilometer.domain.home.keyVisual.dto.KeyVisualUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeService {

    private final KeyVisualRepository keyVisualRepository;

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
}
