package com.kilometer.domain.archive.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemArchiveDto {

    private Long id;
    private String name;
    private String imageUrl;
    private LocalDateTime updatedAt;
    private Integer starRating;
    private Integer heartCount;
    private String comment;
    private boolean isHeart;

}
