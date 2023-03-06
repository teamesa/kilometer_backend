package com.kilometer.common;

import com.kilometer.backend.controller.dto.module.monthlyfreeticket.MonthlyFreeTicketsResponse;
import com.kilometer.domain.archive.dto.RealTimeArchiveDto;
import com.kilometer.domain.homeModules.ModuleResponseDto;
import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.homeModules.modules.monthlyFreeTicket.dto.MonthlyFreeTicketResponse;
import com.kilometer.domain.homeModules.modules.realTimeArchive.dto.RealTimeArchiveResponse;
import com.kilometer.domain.item.dto.MonthlyFreeTicketDto;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
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
    public static final ModuleType MONTHLY_FREE_TICKET = ModuleType.MONTHLY_FREE_ITEM;
    public static final int INDEX = 1;
    public static final String ITEM_DETAIL_URL = "/detail/" + ITEM_ID;
    public static final String DIM_COLOR = "#fff";
    public static final String OPACITY = "0.8";
    public static final String THUMBNAIL_IMAGE_URL = "thumbnailImageUrl";
    public static final int PICK_COUNT = 4;
    public static final boolean IS_HEARTED = true;
    public static final Long ARCHIVE_COUNT = 4L;
    public static final double GRADE = 3.5;
    public static final ExhibitionType EXHIBITION_TYPE = ExhibitionType.EXHIBITION;

    public static final RealTimeArchiveDto REAL_TIME_ARCHIVE_DTO = RealTimeArchiveDto.builder()
            .archiveId(1L)
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

    public static final ModuleResponseDto<RealTimeArchiveResponse> REAL_TIME_ARCHIVE_RESPONSE_MODULE_RESPONSE_DTO = ModuleResponseDto.of(
            REAL_TIME_ARCHIVE, INDEX, REAL_TIME_ARCHIVE_RESPONSE);

    public static final ModuleResponseDto<Object> REAL_TIME_ARCHIVE_HOME_MODULE_RESPONSE_DTO = ModuleResponseDto.of(
            REAL_TIME_ARCHIVE, INDEX, REAL_TIME_ARCHIVE_RESPONSE);

    public static final MonthlyFreeTicketDto MONTHLY_FREE_TICKET_DTO = MonthlyFreeTicketDto.builder()
            .itemId(ITEM_ID)
            .thumbnailImageUrl(THUMBNAIL_IMAGE_URL)
            .title(TITLE)
            .exhibitionType(EXHIBITION_TYPE)
            .exposureType(ExposureType.ON)
            .feeType(FeeType.FREE)
            .pickCount(PICK_COUNT)
            .isHearted(IS_HEARTED)
            .archiveCount(ARCHIVE_COUNT)
            .grade(GRADE)
            .build();

    public static final MonthlyFreeTicketResponse MONTHLY_FREE_TICKET_RESPONSE = MonthlyFreeTicketResponse.builder()
            .topTitle(TOP_TITLE)
            .bottomTitle(BOTTOM_TITLE)
            .monthlyFreeTicketDtos(List.of(MONTHLY_FREE_TICKET_DTO))
            .build();

    public static final ModuleResponseDto<MonthlyFreeTicketResponse> MONTHLY_FREE_TICKET_RESPONSE_MODULE_RESPONSE_DTO = ModuleResponseDto.of(
            MONTHLY_FREE_TICKET, INDEX, MONTHLY_FREE_TICKET_RESPONSE
    );
}
