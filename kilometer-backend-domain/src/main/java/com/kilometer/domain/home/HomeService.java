package com.kilometer.domain.home;

import com.kilometer.domain.home.keyVisual.KeyVisual;
import com.kilometer.domain.home.keyVisual.KeyVisualRepository;
import com.kilometer.domain.home.keyVisual.dto.KeyVisualResponse;
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

    public List<KeyVisualResponse> findAll() {
        return keyVisualRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream()
                .map(KeyVisual::makeResponse)
                .collect(Collectors.toList());
    }
}
