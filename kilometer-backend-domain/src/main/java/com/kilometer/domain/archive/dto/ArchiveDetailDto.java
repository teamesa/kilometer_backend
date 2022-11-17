package com.kilometer.domain.archive.dto;

import com.kilometer.domain.item.enumType.ExhibitionType;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArchiveDetailDto {
    private Long id;
    private ExhibitionType exhibitionType;
    private LocalDateTime updatedAt;
    private String title;
    private String comment;
    private int starRating;
    private boolean isWrited;
    private boolean isVisibleAtItem;
}
