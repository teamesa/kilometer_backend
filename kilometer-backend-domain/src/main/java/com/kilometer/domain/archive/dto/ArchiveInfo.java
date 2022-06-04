package com.kilometer.domain.archive.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveInfo {
    private Long id;
    private String userProfileUrl;
    private String userName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updatedAt;
    private Integer starRating;
    private Integer heartCount;
    private Boolean isHearted=false;
    private String comment;
    private String food;
    private String cafe;
}
