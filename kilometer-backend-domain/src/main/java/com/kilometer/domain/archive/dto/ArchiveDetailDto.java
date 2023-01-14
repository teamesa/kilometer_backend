package com.kilometer.domain.archive.dto;

import com.kilometer.domain.item.enumType.ExhibitionType;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArchiveDetailDto {
    private Long id;
    private Long itemId;
    private String itemListImageUrl;
    private ExhibitionType itemExhibitionType;
    private String itemTitle;
    private LocalDateTime updatedAt;

    private String comment;
    private int starRating;
    private boolean isWrited;
    private boolean isVisibleAtItem;
}
