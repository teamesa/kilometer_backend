package com.kilometer.domain.crawledItem;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.kilometer.common.Fixture;
import com.kilometer.common.annotation.SpringTestWithData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@DisplayName("CrawledItemService 는 ")
@SpringTestWithData
class CrawledItemServiceTest {

    @Autowired
    private CrawledItemRepository crawledItemRepository;

    @Autowired
    private CrawledItemService crawledItemService;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @DisplayName("CrawledItem을 저장해야 한다.")
    @Test
    void saveCrawledItemTest() {
        crawledItemService.saveCrawledItem(Fixture.CRAWLED_ITEM_DTO);

        (new TransactionTemplate(platformTransactionManager)).execute((status) -> {
            CrawledItem actual = crawledItemRepository.getById(1L);
            assertAll(
                    () -> assertThat(actual.getId()).isEqualTo(1L),
                    () -> assertThat(actual.getCrawledItemDetailImages().size()).isEqualTo(5)
            );
            return null;
        });
    }

    @DisplayName("같은 CrawledItem이 존재하는지 확인해야 한다.")
    @Test
    void hasDuplicatedCrawledItem() {
        crawledItemRepository.save(Fixture.CRAWLED_ITEM_DTO.toEntity());

        boolean actual = crawledItemService.hasDuplicatedCrawledItem(Fixture.CRAWLED_ITEM_DTO);

        assertThat(actual).isTrue();
    }
}
