package com.kilometer.domain.item;

public enum RegionType {

    SEOUL("서울"), GYEONGGI("경기"), GANGWON("강원"),
    CHUNGCHEONG("충청(대전 포함)"), GYEONGSANG("경상(대구, 울산, 부산 포함)"),
    JEOLLA("전라(광주 포함)"), JEJU("제주");

    private final String description;

    RegionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
