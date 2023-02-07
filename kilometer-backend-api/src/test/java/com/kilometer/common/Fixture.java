package com.kilometer.common;

import com.kilometer.domain.archive.dto.RealTimeArchiveDto;
import com.kilometer.domain.homeModules.ModuleResponseDto;
import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.homeModules.modules.realTimeArchive.dto.RealTimeArchiveResponse;
import java.time.LocalDateTime;
import java.util.List;

public class Fixture {

    public static final int LIKE_COUNT = 3;
    public static final int STAR_RATING = 5;
    public static final String COMMENT = "commnet";
    public static final String IMAGE_URL = "imageURL";
    public static final String PLACE_NAME = "placeName";
    public static final String TITLE = "title";
    public static final String USER_IMAGE_URL = "userImageUrl";
    public static final String USER_NAME = "userName";
    public static final Long ITEM_ID = 1L;
    public static final Long USER_ID = 1L;
    public static final boolean IS_LIKED = true;
    public static final String TOP_TITLE = "topTitle";
    public static final String BOTTOM_TITLE = "bottomTitle";
    public static final ModuleType REAL_TIME_ARCHIVE = ModuleType.REAL_TIME_ARCHIVE;
    public static final int INDEX = 1;
    public static final String ITEM_DETAIL_URL = "/detail/" + ITEM_ID;
    public static final String DIM_COLOR = "#fff";
    public static final String OPACITY = "0.8";

    public static final RealTimeArchiveDto REAL_TIME_ARCHIVE_DTO = RealTimeArchiveDto.builder()
            .likeCount(LIKE_COUNT)
            .starRating(STAR_RATING)
            .updatedAt(LocalDateTime.now())
            .comment(COMMENT)
            .imageUrl(IMAGE_URL)
            .placeName(PLACE_NAME)
            .title(TITLE)
            .itemId(ITEM_ID)
            .userId(USER_ID)
            .userImageUrl(USER_IMAGE_URL)
            .userName(USER_NAME)
            .isLiked(IS_LIKED)
            .build();

    public static final RealTimeArchiveResponse REAL_TIME_ARCHIVE_RESPONSE = RealTimeArchiveResponse.builder()
            .topTitle(TOP_TITLE)
            .bottomTitle(BOTTOM_TITLE)
            .archives(List.of(REAL_TIME_ARCHIVE_DTO))
            .build();

    public static final ModuleResponseDto<RealTimeArchiveResponse> MODULE_RESPONSE_DTO = ModuleResponseDto.of(
            REAL_TIME_ARCHIVE, INDEX, REAL_TIME_ARCHIVE_RESPONSE);

    public static final ModuleResponseDto<Object> HOME_MODULE_RESPONSE_DTO = ModuleResponseDto.of(
            REAL_TIME_ARCHIVE, INDEX, REAL_TIME_ARCHIVE_RESPONSE);
}
