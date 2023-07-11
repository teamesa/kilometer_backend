package com.kilometer.backend.batch.crawler.util;

import com.kilometer.domain.item.enumType.ExhibitionType;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Yes24ExhibitionTypeConverter {

    public static final Map<String, String> cache;

    static {
        cache = new HashMap<>();
        cache.put("콘서트", ExhibitionType.CONCERT.name());
        cache.put("전시/행사", ExhibitionType.EXHIBITION.name());
        cache.put("뮤지컬", ExhibitionType.MUSICAL.name());
    }

   public static String of(final String key) {
        return cache.getOrDefault(key, ExhibitionType.ALL.name());
   }
}
