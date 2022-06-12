package com.kilometer.domain.archive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
@AllArgsConstructor
public enum ArchiveSortType {
    MODIFY_DESC("수정순","updatedAt", Sort.Direction.DESC),
    LIKE_DESC("좋아요순","heartCount", Sort.Direction.DESC),
    ;

    private final String description;
    private final String sortedColumnName;
    private final Sort.Direction direction;

    public Sort getArchiveSortOption() {
        return Sort.by(getDirection(), getSortedColumnName());
    }
}
