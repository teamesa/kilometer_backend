package com.kilometer.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum SearchSortType {
    END_DATE_ASC("종료 임박순", "endDate", Sort.Direction.ASC),
    HEART_DESC("하트PICK순", "pick", Sort.Direction.DESC),
    GRADE_DESC("별점순", "grade", Sort.Direction.DESC),
    ENROLL_DESC("등록순", "id", Sort.Direction.DESC),
    ;

    private String description;
    private String sortedColumnName;
    private Sort.Direction direction;

    public Sort getSearchSortOption() {
        return Sort.by(getDirection(), getSortedColumnName());
    }
}
