package com.kilometer.domain.archive.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kilometer.domain.archive.like.dto.ArchiveLike;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveUpdateResponse {

    private Long id;
    private String userProfileUrl;
    private String userName;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDateTime updatedAt;
    private Integer starRating;
    private Integer likeCount;
    private ArchiveLike heart;
    private String comment;
    private List<PlaceInfo> placeInfos;
    @Builder.Default
    private List<String> photoUrls = List.of();
}
