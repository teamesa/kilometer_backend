package com.kilometer.domain.archive.dto;

import com.kilometer.domain.item.enumType.ExhibitionType;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyArchiveDto {
    private Long id;
    private String title;
    private ExhibitionType exhibitionType;
    private String thumbnailImageUrl;
    private String comment;
    private LocalDateTime updatedAt;
}
