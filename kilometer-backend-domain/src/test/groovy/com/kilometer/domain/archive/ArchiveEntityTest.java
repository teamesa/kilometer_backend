package com.kilometer.domain.archive;

import static com.kilometer.common.statics.Statics.아카이브_이미지_URL;
import static com.kilometer.common.statics.Statics.카페_이름;
import static org.assertj.core.api.Assertions.assertThat;

import com.kilometer.domain.archive.archiveImage.ArchiveImageEntity;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlaceEntity;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArchiveEntityTest {

    private final ArchiveImageEntity 전시회_사진 = ArchiveImageEntity.builder()
        .imageUrl(아카이브_이미지_URL)
        .build();
    private final UserVisitPlaceEntity 근처_맛집_사진 = UserVisitPlaceEntity.builder()
        .placeName(카페_이름)
        .build();
    private final List<ArchiveImageEntity> 전시회_사진들 = List.of(전시회_사진);
    private final List<UserVisitPlaceEntity> 근처_맛집_사진들 = List.of(근처_맛집_사진);

    @Test
    @DisplayName("ArchiveEntity에 ArchiveImageEntities를 추가한다.")
    void addArchiveImages() {
        // given
        ArchiveEntity archiveEntity = ArchiveEntity.builder()
            .build();

        // when
        archiveEntity.addArchiveImages(전시회_사진들);

        // then
        assertThat(archiveEntity.getArchiveImages()).hasSize(1);
    }

    @Test
    @DisplayName("ArchiveEntity에 UserVisitPlaces를 추가한다.")
    void addUserVisitPlaces() {
        // given
        ArchiveEntity archiveEntity = ArchiveEntity.builder()
            .build();

        // when
        archiveEntity.addUserVisitPlaces(근처_맛집_사진들);

        // then
        assertThat(archiveEntity.getUserVisitPlaces()).hasSize(1);
    }
}
