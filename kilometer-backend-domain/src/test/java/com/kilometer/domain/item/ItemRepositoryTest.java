package com.kilometer.domain.item;

import static com.kilometer.common.Fixture.COMMENT;
import static com.kilometer.common.Fixture.LIKE_COUNT;
import static com.kilometer.common.Fixture.MAX_DATE;
import static com.kilometer.common.Fixture.MIN_DATE;
import static com.kilometer.common.Fixture.STAR_RATING;
import static com.kilometer.common.Fixture.TITLE;
import static com.kilometer.common.Fixture.USER_IMAGE_URL;
import static com.kilometer.common.Fixture.USER_NAME;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.kilometer.domain.archive.ArchiveEntity;
import com.kilometer.domain.archive.ArchiveRepository;
import com.kilometer.domain.item.dto.MonthlyFreeTicketDto;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.item.enumType.RegionType;
import com.kilometer.domain.pick.Pick;
import com.kilometer.domain.pick.PickRepository;
import com.kilometer.domain.user.User;
import com.kilometer.domain.user.UserRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("ItemRepository 는 ")
@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ArchiveRepository archiveRepository;

    @Autowired
    PickRepository pickRepository;

    @DisplayName("이달의 무료 티켓을 최대 5개 까지 랜덤하게 가져온다.")
    @Test
    void findTopRandomFiveMonthlyFreeTicket() {
        User savedUser = saveUser();
        for (int i = 0; i < 7; i++) {
            ItemEntity savedItemEntity = saveItem();
            savePick(savedUser, savedItemEntity);
            saveArchive(savedUser, savedItemEntity);
        }

        List<MonthlyFreeTicketDto> monthlyFreeTicketDtos = itemRepository.findTopRandomFiveMonthlyFreeTicket(
                LocalDate.now());

        assertThat(monthlyFreeTicketDtos.size()).isEqualTo(5);
    }

    private User saveUser() {
        User user = User.builder()
                .name(USER_NAME)
                .email("email@gmail.com")
                .imageUrl(USER_IMAGE_URL)
                .build();
        return userRepository.save(user);
    }

    private ItemEntity saveItem() {
        ItemEntity item = ItemEntity.builder()
                .exhibitionType(ExhibitionType.EXHIBITION)
                .exposureType(ExposureType.ON)
                .regionType(RegionType.CHUNGCHEONG)
                .feeType(FeeType.FREE)
                .listImageUrl("listImageUrl")
                .thumbnailImageUrl("thumbnailImageUrl")
                .title(TITLE)
                .placeName("place")
                .latitude(12.34)
                .longitude(45.67)
                .price("123")
                .homepageUrl("hompageUrl")
                .ticketUrl("ticketUrl")
                .operatingTime("operatingTIme")
                .pickCount(1)
                .startDate(MIN_DATE)
                .endDate(MAX_DATE)
                .build();
        return itemRepository.save(item);
    }

    private Pick savePick(User user, ItemEntity itemEntity) {
        Pick pick = Pick.builder()
                .isHearted(true)
                .pickedItem(itemEntity)
                .pickedUser(user)
                .build();
        return pickRepository.save(pick);
    }

    private ArchiveEntity saveArchive(User user, ItemEntity itemEntity) {
        ArchiveEntity archive = ArchiveEntity.builder()
                .starRating(STAR_RATING)
                .likeCount(LIKE_COUNT)
                .isVisibleAtItem(true)
                .comment(COMMENT)
                .user(user)
                .item(itemEntity)
                .build();
        return archiveRepository.save(archive);
    }
}
