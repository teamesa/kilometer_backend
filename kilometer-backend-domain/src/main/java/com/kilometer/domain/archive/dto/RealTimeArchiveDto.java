package com.kilometer.domain.archive.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RealTimeArchiveDto {

    private int likeCount;
    private int starRating;
    private LocalDateTime updatedAt;
    private String comment;
    private String imageUrl;
    private String placeName;
    private String title;
    private Long itemId;
    private Long userId;
    private String userImageUrl;
    private String userName;
    private boolean isLiked;
}
