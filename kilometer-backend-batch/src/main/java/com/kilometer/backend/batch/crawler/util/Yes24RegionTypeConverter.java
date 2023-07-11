package com.kilometer.backend.batch.crawler.util;

import com.kilometer.domain.item.enumType.RegionType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Yes24RegionTypeConverter {

    private static final Map<List<String>, String> cache;

    static {
        cache = new HashMap<>();
        cache.put(List.of("서울", "서울특별시"), RegionType.SEOUL.name());
        cache.put(List.of("경기", "경기도", "인천", "인천광역시"), RegionType.GYEONGGI.name());
        cache.put(List.of("대전", "세종특별자치시", "세종", "충청북도", "충청남도"), RegionType.CHUNGCHEONG.name());
        cache.put(List.of("부산", "부산광역시", "울산", "울산광역시",
                "경남", "경상남도", "대구", "대구광역시", "경북", "경상북도"), RegionType.GYEONGSANG.name());
        cache.put(List.of("전북", "전라북도", "광주", "광주광역시", "전남", "전라남도"), RegionType.JEOLLA.name());
        cache.put(List.of("제주특별자치도", "제주"), RegionType.JEJU.name());
    }

    public static String of(final String regionName) {
        return cache.entrySet()
                .stream()
                .filter(entrySet -> entrySet.getKey().contains(regionName))
                .findFirst()
                .map(Entry::getValue)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지역입니다."));
    }
}
