package com.kilometer.domain.archive.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RealTimeArchiveDto {

    private int likeCount;
    private int starRating;
    private LocalDateTime updatedAt;
    private String comment;
    private String imageUrl;
    private String placeName;
    private String title;
    private Long itemId;
    private String userImageUrl;
    private String userName;
}
