package com.kilometer.domain.item.enumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegionType {

    SEOUL("서울", "서울"),
    GYEONGGI("경기", "경기"),
    GANGWON("강원", "강원"),
    CHUNGCHEONG("충청(대전, 세종 포함)", "충청"),
    GYEONGSANG("경상(대구, 울산, 부산 포함)", "경상"),
    JEOLLA("전라(광주 포함)", "전라"),
    JEJU("제주", "제주"),
    ;

    private final String description;
    private final String frontName;
}
