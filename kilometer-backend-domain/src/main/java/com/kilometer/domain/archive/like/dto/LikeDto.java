package com.kilometer.domain.archive.like.dto;

import com.kilometer.domain.archive.like.Like;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LikeDto {

    private Long id;
    private boolean isLiked = false;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public static LikeDto of(Like entity) {
        return new LikeDto(entity.getId(), entity.isLiked(), entity.getCreatedAt(),
            entity.getUpdatedAt());
    }
}
