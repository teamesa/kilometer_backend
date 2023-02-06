package com.kilometer.archive;

import static com.kilometer.common.Fixture.ARCHIVE_IMAGE_URL;
import static com.kilometer.common.Fixture.COMMENT;
import static com.kilometer.common.Fixture.LIKE_COUNT;
import static com.kilometer.common.Fixture.PLACE_NAME;
import static com.kilometer.common.Fixture.STAR_RATING;
import static com.kilometer.common.Fixture.TITLE;
import static com.kilometer.common.Fixture.USER_IMAGE_URL;
import static com.kilometer.common.Fixture.USER_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.kilometer.domain.archive.ArchiveEntity;
import com.kilometer.domain.archive.ArchiveRepository;
import com.kilometer.domain.archive.PlaceType;
import com.kilometer.domain.archive.archiveImage.ArchiveImageEntity;
import com.kilometer.domain.archive.archiveImage.ArchiveImageRepository;
import com.kilometer.domain.archive.dto.RealTimeArchiveDto;
import com.kilometer.domain.archive.like.Like;
import com.kilometer.domain.archive.like.LikeRepository;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlaceEntity;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlaceRepository;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.item.ItemRepository;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.item.enumType.RegionType;
import com.kilometer.domain.user.User;
import com.kilometer.domain.user.UserRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("ArchiveRepository 는 ")
@DataJpaTest
class ArchiveRepositoryTest {

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

    @Autowired
    LikeRepository likeRepository;

    @DisplayName("실시간 아카이브에 필요한 데이터를 조회한다.")
    @Test
    void findReqlTimeArchive() {
        User savedUser = saveUser();
        ArchiveEntity savedArchive = saveArchive(savedUser);
        saveUserVisitPlace(savedArchive);
        saveArchiveImage(savedArchive);
        saveArchiveLike(savedArchive, savedUser);

        RealTimeArchiveDto realTimeArchiveDto = archiveRepository.findRealTimeArchive(savedArchive.getId())
                .get();

        assertAll(
                () -> assertThat(realTimeArchiveDto.getLikeCount()).isEqualTo(LIKE_COUNT),
                () -> assertThat(realTimeArchiveDto.getStarRating()).isEqualTo(STAR_RATING),
                () -> assertThat(realTimeArchiveDto.getComment()).isEqualTo(COMMENT),
                () -> assertThat(realTimeArchiveDto.getImageUrl()).isEqualTo(ARCHIVE_IMAGE_URL),
                () -> assertThat(realTimeArchiveDto.getPlaceName()).isEqualTo(PLACE_NAME),
                () -> assertThat(realTimeArchiveDto.getTitle()).isEqualTo(TITLE),
                () -> assertThat(realTimeArchiveDto.getUserId()).isEqualTo(savedUser.getId()),
                () -> assertThat(realTimeArchiveDto.getUserImageUrl()).isEqualTo(USER_IMAGE_URL),
                () -> assertThat(realTimeArchiveDto.getUserName()).isEqualTo(USER_NAME),
                () -> assertThat(realTimeArchiveDto.isLiked()).isTrue()
        );
    }

    @DisplayName("아카이브 이미지가 존재하는 아카이브들을 최대 4개 조회한다.")
    @Test
    void findTopFourArchievsWithImageUrl() {
        User savedUser = saveUser();
        for (int i = 0; i < 6; i++) {
            ArchiveEntity savedArchiveEntity = saveArchive(savedUser);
            saveArchiveImage(savedArchiveEntity);
        }

        List<ArchiveEntity> topFourArchivesWithImageUrl = archiveRepository.findTopFourArchivesWithImageUrl();

        assertThat(topFourArchivesWithImageUrl.size()).isEqualTo(4);
    }

    private User saveUser() {
        User user = User.builder()
                .name(USER_NAME)
                .email("email@gmail.com")
                .imageUrl(USER_IMAGE_URL)
                .build();
        return userRepository.save(user);
    }

    private ArchiveEntity saveArchive(User user) {
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

        ArchiveEntity archive = ArchiveEntity.builder()
                .starRating(STAR_RATING)
                .likeCount(LIKE_COUNT)
                .isVisibleAtItem(true)
                .comment(COMMENT)
                .user(user)
                .item(savedItem)
                .build();
        return archiveRepository.save(archive);
    }

    private void saveArchiveImage(final ArchiveEntity savedArchive) {
        ArchiveImageEntity archiveImage = ArchiveImageEntity.builder()
                .imageUrl(ARCHIVE_IMAGE_URL)
                .archiveEntity(savedArchive)
                .build();
        archiveImageRepository.save(archiveImage);
    }

    private void saveUserVisitPlace(final ArchiveEntity savedArchive) {
        UserVisitPlaceEntity userVisitPlace = UserVisitPlaceEntity.builder()
                .placeType(PlaceType.CAFE)
                .placeName(PLACE_NAME)
                .address("address")
                .roadAddress("roadAddress")
                .archiveEntity(savedArchive)
                .build();
        userVisitPlaceRepository.save(userVisitPlace);
    }

    private void saveArchiveLike(ArchiveEntity archive, User user) {
        Like like = Like.builder()
                .likedUser(user)
                .likedArchiveEntity(archive)
                .isLiked(true)
                .build();
        likeRepository.save(like);
    }
}
