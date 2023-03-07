package com.kilometer.domain.archive.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kilometer.domain.item.dto.ItemForArchive;
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
    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDateTime updatedAt;
    private String comment;
    private int starRating;
    private List<PlaceInfo> placeInfos;
    private ItemForArchive item;
    private List<String> photoUrls;
    private List<LinkInfo> archiveActionButton;
    private boolean visibleAtItem;
}
