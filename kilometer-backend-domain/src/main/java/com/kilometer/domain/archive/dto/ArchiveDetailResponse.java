package com.kilometer.domain.archive.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kilometer.domain.badge.ItemBadge;
import com.kilometer.domain.linkInfo.LinkInfo;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ArchiveDetailResponse {
    private ItemBadge typeBadge;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDateTime updatedAt;
    private String title;
    private String comment;
    private int starRating;
    private String food;
    private String cafe;
    private Long itemId;
    private List<String> photoUrls;
    private List<LinkInfo> archiveAdditionalInfos;
    private boolean visibleAtItem;
}
