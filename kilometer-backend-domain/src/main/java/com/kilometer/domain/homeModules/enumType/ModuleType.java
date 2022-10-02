package com.kilometer.domain.homeModules.enumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ModuleType {

    EMPTY("선택"), KEY_VISUAL("키비주얼"), SWIPE_ITEM("전시글 모듈"),
    MONTHLY_FREE_ITEM("프리 티켓"), REAL_TIME_ARCHIVE("실시간 아카이브"),
    UPCOMING_ITEM("두근두근 진행 예정");

    private final String description;
}
