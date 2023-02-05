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
import com.kilometer.domain.archive.Archive;
import com.kilometer.domain.archive.ArchiveRepository;
import com.kilometer.domain.archive.PlaceType;
import com.kilometer.domain.archive.archiveImage.ArchiveImage;
import com.kilometer.domain.archive.archiveImage.ArchiveImageRepository;
import com.kilometer.domain.archive.dto.RealTimeArchiveDto;
import com.kilometer.domain.archive.like.Like;
import com.kilometer.domain.archive.like.LikeRepository;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlace;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlaceRepository;
import com.kilometer.domain.homeModules.ModuleParamDto;
import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.homeModules.modules.Module;
import com.kilometer.domain.homeModules.modules.dto.ModuleDto;
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
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @DisplayName("실시간 아키이브에 대한 정보를 가져온다.")
    @Test
    void generateRealTimeArchives() {
        User savedUser = saveUser();
        Archive savedArchive = saveArchive(savedUser);
        saveUserVisitPlace(savedArchive);
        saveArchiveImage(savedArchive);
        saveArchiveLike(savedArchive, savedUser);

        Module module = Module.builder()
                .moduleName(ModuleType.REAL_TIME_ARCHIVE)
                .upperModuleTitle("upperTitle")
                .lowerModuleTitle("lowerModuleTitle")
                .build();
        ModuleParamDto moduleParamDto = ModuleParamDto.of(LocalDateTime.now(), savedUser.getId()+1, ModuleDto.from(module));
        RealTimeArchiveResponse realTimeArchiveResponse = (RealTimeArchiveResponse)realTimeArchiveHandler.generator(moduleParamDto);

        assertAll(
                () -> assertThat(realTimeArchiveResponse.getArchives().size()).isEqualTo(1),
                () -> assertThat(realTimeArchiveResponse.getTopTitle()).isEqualTo("upperTitle"),
                () -> assertThat(realTimeArchiveResponse.getBottomTitle()).isEqualTo("lowerModuleTitle")
        );
    }

    private User saveUser() {
        User user = User.builder()
                .name(USER_NAME)
                .email("email@gmail.com")
                .imageUrl(USER_IMAGE_URL)
                .build();
        return userRepository.save(user);
    }

    private Archive saveArchive(User user) {
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

        Archive archive = Archive.builder()
                .starRating(STAR_RATING)
                .likeCount(LIKE_COUNT)
                .isVisibleAtItem(true)
                .comment(COMMENT)
                .user(user)
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

    public void saveArchiveLike(Archive archive, User user) {
        Like like = Like.builder()
                .likedUser(user)
                .likedArchive(archive)
                .isLiked(true)
                .build();
        likeRepository.save(like);
    }
}
