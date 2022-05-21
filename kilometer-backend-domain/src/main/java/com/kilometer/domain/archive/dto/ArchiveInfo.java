package com.kilometer.domain.archive.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@AllArgsConstructor
public class ArchiveInfo {
    private String userProfileUrl;
    private String userName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
    private double startRating;
    private int heartCount;
    private boolean isHearted;
    private String comment;
    private String food;
    private String cafe;
}
