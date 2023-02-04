package com.kilometer.domain.archive;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.kilometer.domain.archive.archiveImage.ArchiveImage;
import com.kilometer.domain.archive.archiveImage.ArchiveImageRepository;
import com.kilometer.domain.archive.dto.RealTimeArchiveDto;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlace;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlaceRepository;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.item.ItemRepository;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.item.enumType.RegionType;
import com.kilometer.domain.user.Role;
import com.kilometer.domain.user.User;
import com.kilometer.domain.user.UserRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("ArchiveRepository 는 ")
@DataJpaTest
class ArchiveRepositoryTest {
    
    private static final int LIKE_COUNT = 3;
    private static final int STAR_RATING = 5;
    private static final String COMMENT = "comment";
    private static final String PLACE_NAME = "placeName";
    private static final String TITLE = "title";
    private static final String ARCHIVE_IMAGE_URL = "archiveImageUrl";
    private static final String USER_IMAGE_URL = "userImageUrl";
    private static final String USER_NAME = "userName";

    @Autowired
    ArchiveImageRepository archiveImageRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ArchiveRepository archiveRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserVisitPlaceRepository userVisitPlaceRepository;

    @DisplayName("실시간 아카이브에 필요한 데이터를 조회한다.")
    @Test
    void findReqlTimeArchive() {
        
        Archive savedArchive = saveArchive();
        saveUserVisitPlace(savedArchive);
        saveArchiveImage(savedArchive);

        RealTimeArchiveDto realTimeArchiveDto = archiveRepository.findRealTimeArchive(savedArchive.getId())
                .get();

        assertAll(
                () -> assertThat(realTimeArchiveDto.getLikeCount()).isEqualTo(LIKE_COUNT),
                () -> assertThat(realTimeArchiveDto.getStarRating()).isEqualTo(STAR_RATING),
                () -> assertThat(realTimeArchiveDto.getComment()).isEqualTo(COMMENT),
                () -> assertThat(realTimeArchiveDto.getImageUrl()).isEqualTo(ARCHIVE_IMAGE_URL),
                () -> assertThat(realTimeArchiveDto.getPlaceName()).isEqualTo(PLACE_NAME),
                () -> assertThat(realTimeArchiveDto.getTitle()).isEqualTo(TITLE),
                () -> assertThat(realTimeArchiveDto.getUserImageUrl()).isEqualTo(USER_IMAGE_URL),
                () -> assertThat(realTimeArchiveDto.getUserName()).isEqualTo(USER_NAME)
        );
    }

    private Archive saveArchive() {
        ItemEntity item = ItemEntity.builder()
                .exhibitionType(ExhibitionType.EXHIBITION)
                .exposureType(ExposureType.ON)
                .regionType(RegionType.CHUNGCHEONG)
                .feeType(FeeType.COST)
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
                .startDate(LocalDate.MIN)
                .endDate(LocalDate.MAX)
                .build();
        ItemEntity savedItem = itemRepository.save(item);

        User user = User.builder()
                .name(USER_NAME)
                .email("email@gmail.com")
                .imageUrl(USER_IMAGE_URL)
                .role(Role.USER)
                .build();
        User savedUser = userRepository.save(user);

        Archive archive = Archive.builder()
                .starRating(STAR_RATING)
                .likeCount(LIKE_COUNT)
                .isVisibleAtItem(true)
                .comment(COMMENT)
                .user(savedUser)
                .item(savedItem)
                .build();
        return archiveRepository.save(archive);
    }

    private void saveArchiveImage(final Archive savedArchive) {
        ArchiveImage archiveImage = ArchiveImage.builder()
                .imageUrl(ARCHIVE_IMAGE_URL)
                .archive(savedArchive)
                .build();
        archiveImageRepository.save(archiveImage);
    }

    private void saveUserVisitPlace(final Archive savedArchive) {
        UserVisitPlace userVisitPlace = UserVisitPlace.builder()
                .placeType(PlaceType.CAFE)
                .placeName(PLACE_NAME)
                .address("address")
                .roadAddress("roadAddress")
                .archive(savedArchive)
                .build();
        userVisitPlaceRepository.save(userVisitPlace);
    }
}
