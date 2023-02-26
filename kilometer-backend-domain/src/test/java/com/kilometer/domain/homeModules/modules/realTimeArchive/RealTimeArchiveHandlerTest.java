package com.kilometer.domain.homeModules.modules.realTimeArchive;

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

import com.kilometer.common.annotation.SpringTestWithData;
import com.kilometer.domain.archive.ArchiveEntity;
import com.kilometer.domain.archive.ArchiveRepository;
import com.kilometer.domain.archive.domain.userVisitPlace.PlaceType;
import com.kilometer.domain.archive.archiveImage.ArchiveImageEntity;
import com.kilometer.domain.archive.archiveImage.ArchiveImageRepository;
import com.kilometer.domain.archive.like.Like;
import com.kilometer.domain.archive.like.LikeRepository;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlaceEntity;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlaceRepository;
import com.kilometer.domain.homeModules.ModuleParamDto;
import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.homeModules.modules.Module;
import com.kilometer.domain.homeModules.modules.dto.ModuleDto;
import com.kilometer.domain.homeModules.modules.realTimeArchive.RealTimeArchiveHandler;
import com.kilometer.domain.homeModules.modules.realTimeArchive.dto.RealTimeArchiveResponse;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.item.ItemRepository;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.item.enumType.RegionType;
import com.kilometer.domain.user.User;
import com.kilometer.domain.user.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("RealTimeArchiveHandler 는 ")
@SpringTestWithData
class RealTimeArchiveHandlerTest {

    @Autowired
    RealTimeArchiveHandler realTimeArchiveHandler;

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

    @DisplayName("모듈 유형이 실시간 아카이브인지 확인한다.")
    @Test
    void isRealTimeArchive() {
        boolean actual = realTimeArchiveHandler.supports(ModuleType.REAL_TIME_ARCHIVE);

        assertThat(actual).isTrue();
    }

    @DisplayName("실시간 아키이브에 대한 정보를 가져올 때.")
    @Nested
    class RealTimeArchiveGenerationTest {

        @DisplayName("모든 데이터가 존재하면 실시간 아카이브 정보를 가져온다.")
        @Test
        void generateRealTimeArchivesWithAllData() {
            User savedUser = saveUser();
            ArchiveEntity savedArchive = saveArchive(savedUser);
            saveUserVisitPlace(savedArchive);
            saveArchiveImage(savedArchive);
            saveArchiveLike(savedArchive, savedUser);
            RealTimeArchiveResponse savedRealTimeArchiveResponse = (RealTimeArchiveResponse) saveRealTImeArchiveResponse(savedUser)
                    .get();

            assertAll(
                    () -> assertThat(savedRealTimeArchiveResponse.getArchives().size()).isEqualTo(1),
                    () -> assertThat(savedRealTimeArchiveResponse.getTopTitle()).isEqualTo("upperTitle"),
                    () -> assertThat(savedRealTimeArchiveResponse.getBottomTitle()).isEqualTo("lowerModuleTitle")
            );
        }

        @DisplayName("아카이브 이미지가 존재하지 않으면 아카이브를 가져오지 않는다.")
        @Test
        void getArchiveThatDoesNotHaveImage() {
            User savedUser = saveUser();
            ArchiveEntity savedArchive = saveArchive(savedUser);
            saveUserVisitPlace(savedArchive);
            saveArchiveLike(savedArchive, savedUser);

            assertThat(saveRealTImeArchiveResponse(savedUser).isEmpty()).isTrue();
        }

        @DisplayName("최대 4개의 실시간 아카이브만 가져온다.")
        @Test
        void getMaxFourArchives() {
            User savedUser = saveUser();
            for (int i = 0; i < 6; i++) {
                ArchiveEntity savedArchive = saveArchive(savedUser);
                saveUserVisitPlace(savedArchive);
                saveArchiveImage(savedArchive);
                saveArchiveLike(savedArchive, savedUser);
            }
            RealTimeArchiveResponse savedRealTimeArchiveResponse = (RealTimeArchiveResponse) saveRealTImeArchiveResponse(savedUser)
                    .get();

            assertThat(savedRealTimeArchiveResponse.getArchives().size()).isEqualTo(4);
        }

        @DisplayName("실시간 아카이브 데이터가 존재하지 않으면 아카이브를 가져오지 않는다.")
        @Test
        void getEmptyRealTimeArchive() {
            User savedUser = saveUser();

            assertThat(saveRealTImeArchiveResponse(savedUser).isEmpty()).isTrue();
        }

        @DisplayName("유저가 로그인 상태이면 좋아요 클릭 여부를 있는 그대로 반환한다.")
        @ParameterizedTest(name = "{index} {displayName} userId={0} isLiked={1}")
        @CsvSource(value = {"1, true", "1, false"})
        void getRealTimeArchiveAfterLoggingIn(Long userId, boolean isLiked) {
            User user = User.builder()
                    .id(userId)
                    .name(USER_NAME)
                    .email("email@gmail.com")
                    .imageUrl(USER_IMAGE_URL)
                    .build();
            User savedUser = userRepository.save(user);

            ArchiveEntity savedArchive = saveArchive(savedUser);
            saveUserVisitPlace(savedArchive);
            saveArchiveImage(savedArchive);

            Like like = Like.builder()
                    .likedUser(user)
                    .likedArchiveEntity(savedArchive)
                    .isLiked(isLiked)
                    .build();
            likeRepository.save(like);
            RealTimeArchiveResponse savedRealTimeArchiveResponse = (RealTimeArchiveResponse) saveRealTImeArchiveResponse(savedUser)
                    .get();

            assertThat(savedRealTimeArchiveResponse.getArchives().get(0).isLiked()).isEqualTo(isLiked);
        }

        @DisplayName("유저가 로그인 상태가 아니면 좋아요 클릭 여부를 클릭하지 않은 상태로 반환한다.")
        @ParameterizedTest(name = "{index} {displayName} userId={0} isLiked={1}")
        @CsvSource(value = {"10, true", "10, false"})
        void getRealTimeArchiveWithoutLogIn(Long userId, boolean isLiked) {
            User savedUser = saveUser();
            ArchiveEntity savedArchive = saveArchive(savedUser);
            saveUserVisitPlace(savedArchive);
            saveArchiveImage(savedArchive);

            Like like = Like.builder()
                    .likedUser(savedUser)
                    .likedArchiveEntity(savedArchive)
                    .isLiked(isLiked)
                    .build();
            likeRepository.save(like);

            User guestUser = User.builder()
                    .id(userId)
                    .name(USER_NAME)
                    .email("email@email.com")
                    .imageUrl(USER_IMAGE_URL)
                    .build();
            RealTimeArchiveResponse savedRealTimeArchiveResponse = (RealTimeArchiveResponse) saveRealTImeArchiveResponse(guestUser)
                    .get();

            assertThat(savedRealTimeArchiveResponse.getArchives().get(0).isLiked()).isFalse();
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

        public void saveArchiveLike(ArchiveEntity archive, User user) {
            Like like = Like.builder()
                    .likedUser(user)
                    .likedArchiveEntity(archive)
                    .isLiked(true)
                    .build();
            likeRepository.save(like);
        }

        private Optional<Object> saveRealTImeArchiveResponse(User user) {
            Module module = Module.builder()
                    .moduleName(ModuleType.REAL_TIME_ARCHIVE)
                    .upperModuleTitle("upperTitle")
                    .lowerModuleTitle("lowerModuleTitle")
                    .build();
            ModuleParamDto moduleParamDto = ModuleParamDto.of(LocalDateTime.now(), user.getId(),
                    ModuleDto.from(module));
            return realTimeArchiveHandler.generator(moduleParamDto);
        }
    }
}
