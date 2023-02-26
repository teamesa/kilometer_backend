package com.kilometer.domain.archive.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kilometer.domain.badge.ItemBadge;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class MyArchiveInfo {
    private String title;
    private String comment;
    private String places;
    private ItemBadge typeBadge;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDateTime updatedAt;
    private boolean existArchiveImages;
    private String listImageUrl;
    private String api;
    private String itemPageUrl;
    private String itemApiUrl;
}
