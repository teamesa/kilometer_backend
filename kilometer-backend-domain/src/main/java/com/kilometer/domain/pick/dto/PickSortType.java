package com.kilometer.domain.pick.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
@AllArgsConstructor
public enum PickSortType {
    MODIFY_DESC("수정순","updatedAt", Sort.Direction.DESC),
    ;

    private final String description;
    private final String sortedColumnName;
    private final Sort.Direction direction;

    public Sort getPickSortOption() {
        return Sort.by(getDirection(), getSortedColumnName());
    }
}
