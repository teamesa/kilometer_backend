package com.kilometer.domain.homeModules.modules.swipeItem;

import com.kilometer.domain.item.enumType.ExhibitionType;
import java.util.List;

public class SwipeItemKeywordGenerator {

    public static List<String> generator(ExhibitionType type, String placeName) {
        return List.of(
            template(type.name()),
            template(placeName)
        );
    }

    private static String template(String name){
        return "#"+name;
    }

}
