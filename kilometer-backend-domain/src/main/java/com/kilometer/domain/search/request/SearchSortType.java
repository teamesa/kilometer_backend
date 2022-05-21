package com.kilometer.domain.search.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
@AllArgsConstructor
public enum SearchSortType {
    END_DATE_ASC("종료 임박순", "endDate", Sort.Direction.ASC),
    HEART_DESC("하트PICK순", "pick", Sort.Direction.DESC),
    GRADE_DESC("별점순", "grade", Sort.Direction.DESC),
    ENROLL_DESC("등록순", "id", Sort.Direction.DESC),
    ;

    private final String description;
    private final String sortedColumnName;
    private final Sort.Direction direction;

    public Sort getSearchSortOption() {
        return Sort.by(getDirection(), getSortedColumnName());
    }
}
