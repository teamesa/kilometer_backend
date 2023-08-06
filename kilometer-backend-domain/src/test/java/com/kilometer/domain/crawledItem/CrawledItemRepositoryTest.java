package com.kilometer.domain.crawledItem;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.kilometer.common.Fixture;
import com.kilometer.domain.item.enumType.RegionType;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DisplayName("CrawledItemRepository 는 ")
@DataJpaTest
class CrawledItemRepositoryTest {

    @Autowired
    private CrawledItemRepository crawledItemRepository;

    @DisplayName("지역, 장소, 시작 날자, 제목을 이용해 데이터를 찾아야 한다.")
    @Test
    void findCralwedItem() {
        CrawledItem crawledItem = Fixture.CRAWLED_ITEM_DTO.toEntity();
        CrawledItem expected = crawledItemRepository.save(crawledItem);

        CrawledItem actual = crawledItemRepository.findByRegionTypeAndPlaceNameAndStartDateAndTitle(
                        RegionType.SEOUL, "국립극장 해오름극장",
                        Fixture.PERFORMANCE_START_DATE, "뮤지컬 <알로하, 나의 엄마들>")
                .get();

        assertThat(expected.getId()).isEqualTo(actual.getId());
    }

    @DisplayName("엔티티를 페이지 형태로 가져와야 한다.")
    @Test
    void findAll() {
        for (int i = 0; i < 100; i++) {
            crawledItemRepository.save(Fixture.CRAWLED_ITEM_DTO.toEntity());
        }

        Page<CrawledItem> actual = crawledItemRepository.findAll(PageRequest.of(1, 10));
        CrawledItem crawledItem = actual.get()
                .collect(Collectors.toList())
                .get(0);

        assertAll(
                () -> assertThat(actual.getTotalElements()).isEqualTo(100),
                () -> assertThat(actual.getTotalPages()).isEqualTo(10),
                () -> assertThat(crawledItem.getId()).isEqualTo(11L)
        );
    }
}
