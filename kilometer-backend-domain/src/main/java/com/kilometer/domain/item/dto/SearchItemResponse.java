package com.kilometer.domain.item.dto;

import com.kilometer.domain.item.ExhibitionType;
import com.kilometer.domain.item.FeeType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SearchItemResponse {
    private Long id;
    private String image;
    private String title;
    private ExhibitionType exhibitionType;
    private FeeType fee;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isHearted;
}
