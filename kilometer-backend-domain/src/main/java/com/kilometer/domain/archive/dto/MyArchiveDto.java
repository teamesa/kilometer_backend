package com.kilometer.domain.archive.dto;

import com.kilometer.domain.item.enumType.ExhibitionType;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyArchiveDto {
    private Long archiveId;
    private Long itemId;
    private String title;
    private ExhibitionType exhibitionType;
    private String listImageUrl;
    private String comment;
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "MyArchiveDto{" +
                "archiveId=" + archiveId +
                ", itemId=" + itemId +'\'' +
                ", title='" + title + '\'' +
                ", exhibitionType=" + exhibitionType +
                ", listImageUrl='" + listImageUrl + '\'' +
                ", comment='" + comment + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
