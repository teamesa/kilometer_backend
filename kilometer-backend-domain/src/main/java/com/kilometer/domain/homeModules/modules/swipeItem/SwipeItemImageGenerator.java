package com.kilometer.domain.homeModules.modules.swipeItem;

import java.util.List;

public class SwipeItemImageGenerator {

    public static String getThumbnailImage(List<String> images) {
        if (images.isEmpty()) {
            return null;
        }
        return images.get(0);
    }

    public static List<String> getImagesExcludeThumbnail(List<String> images) {
        if (images.isEmpty()) {
            return images;
        }
        images.remove(0);
        return images;
    }

}
