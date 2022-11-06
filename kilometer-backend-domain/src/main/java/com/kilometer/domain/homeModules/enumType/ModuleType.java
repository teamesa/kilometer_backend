package com.kilometer.domain.homeModules.enumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ModuleType {

    EMPTY("선택","empty"), KEY_VISUAL("키비주얼","key-visual"), SWIPE_ITEM("전시글 모듈","swipe-item"),
    MONTHLY_FREE_ITEM("프리 티켓","monthly-free-item"), REAL_TIME_ARCHIVE("실시간 아카이브","real-time-archive"),
    UPCOMING_ITEM("두근두근 진행 예정","upcoming-item");

    private final String description;
    private final String frontName;
}
