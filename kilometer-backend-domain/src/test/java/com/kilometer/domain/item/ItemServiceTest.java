package com.kilometer.domain.item;

import com.kilometer.common.annotation.SpringTestWithData;
import com.kilometer.domain.item.dto.ItemInfoResponse;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.item.enumType.RegionType;
import com.kilometer.domain.item.exception.ItemExposureOffException;
import com.kilometer.exception.KilometerErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ItemService는")
@SpringTestWithData
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    private final String DEFAULT_LIST_IMAGE_URL = "리스트 이미지 링크";
    private final String DEFAULT_THUMBNAIL_IMAGE_URL = "상세 이미지 링크";
    private final String DEFAULT_TITLE = "전시글 제목";
    private final String DEFAULT_PLACE_NAME  = "장소 이름";
    private final String DEFAULT_PRICE = "가격";
    private final String DEFAULT_HOME_PAGE_URL = "홈페이지 링크";
    private final String DEFAULT_TICKET_URL = "티켓 구매 링크";
    private final double DEFAULT_LATITUDE = 37.7966264;
    private final double DEFAULT_LONGITUDE = 128.9175799;
    private final String DEFAULT_OPERATION_TIME = "운영 시간";

    @Test
    @DisplayName("전시글을 조회한다.")
    void getItem() {
        // given
        ItemEntity item = ItemEntity.builder()
            .exposureType(ExposureType.ON)
            .exhibitionType(ExhibitionType.EXHIBITION)
            .regionType(RegionType.SEOUL)
            .feeType(FeeType.FREE)
            .listImageUrl(DEFAULT_LIST_IMAGE_URL)
            .thumbnailImageUrl(DEFAULT_THUMBNAIL_IMAGE_URL)
            .title(DEFAULT_TITLE)
            .startDate(LocalDate.of(2023,1,1))
            .endDate(LocalDate.of(2023,2,1))
            .placeName(DEFAULT_PLACE_NAME)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .price(DEFAULT_PRICE)
            .homepageUrl(DEFAULT_HOME_PAGE_URL)
            .ticketUrl(DEFAULT_TICKET_URL)
            .operatingTime(DEFAULT_OPERATION_TIME)
            .build();
        ItemEntity savedItem = itemRepository.save(item);

        // when
        Long userId = -1L;
        ItemInfoResponse actual = itemService.getItem(savedItem.getId(), userId);

        // then
        assertAll(
            () -> assertEquals(actual.getType(), ExhibitionType.EXHIBITION.getDescription()),
            () -> assertEquals(actual.getTitle(), DEFAULT_TITLE),
            () -> assertEquals(actual.getPlace(), DEFAULT_PLACE_NAME),
            () -> assertEquals(actual.getLat(), DEFAULT_LATITUDE),
            () -> assertEquals(actual.getLng(), DEFAULT_LONGITUDE),
            () -> assertEquals(actual.getFeeType(), FeeType.FREE.getDescription()),
            () -> assertEquals(actual.getPrice(), DEFAULT_PRICE),
            () -> assertEquals(actual.getTicketUrl(), DEFAULT_TICKET_URL),
            () -> assertEquals(actual.getTime(), DEFAULT_OPERATION_TIME),
            () -> assertEquals(actual.getHomePageUrl(), DEFAULT_HOME_PAGE_URL),
            () -> assertEquals(actual.getListImageUrl(), DEFAULT_LIST_IMAGE_URL),
            () -> assertEquals(actual.getDetailImageUrl(), DEFAULT_THUMBNAIL_IMAGE_URL),
            () -> assertEquals(actual.getSummary(), ""),
            () -> assertEquals(actual.getPhoto(), List.of())
        );
    }

    @Test
    @DisplayName("미전시된 전시글을 조회할 경우 예외를 반환한다.")
    void getItem_exposureFalse() {
        // given
        ItemEntity item = ItemEntity.builder()
            .exposureType(ExposureType.OFF)
            .build();
        ItemEntity savedItem = itemRepository.save(item);

        // when
        Long userId = -1L;
        ItemExposureOffException actual = assertThrows(ItemExposureOffException.class, () -> itemService.getItem(savedItem.getId(), userId));

        // then
        assertEquals(actual.getErrorCode(), KilometerErrorCode.ITEM_EXPOSURE_OFF);

    }
}