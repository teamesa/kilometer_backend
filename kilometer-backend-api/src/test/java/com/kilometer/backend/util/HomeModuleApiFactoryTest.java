package com.kilometer.backend.util;

import static com.kilometer.common.Fixture.DIM_COLOR;
import static com.kilometer.common.Fixture.INDEX;
import static com.kilometer.common.Fixture.REAL_TIME_ARCHIVE;
import static com.kilometer.common.Fixture.REAL_TIME_ARCHIVE_RESPONSE;
import static com.kilometer.common.Fixture.TITLE;
import static com.kilometer.common.Fixture.TOP_TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.kilometer.backend.controller.dto.module.realtimearchive.ArchivesResponse;
import com.kilometer.domain.homeModules.ModuleResponseDto;
import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.homeModules.modules.realTimeArchive.dto.RealTimeArchiveResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("HomeModuleApiFactory 는 ")
@SpringBootTest
class HomeModuleApiFactoryTest {

    @Autowired
    private HomeModuleApiFactory homeModuleApiFactory;

    @DisplayName("홈 모듈 조회 API를 만들 때 ")
    @Nested
    class ModuleApiCreationTest {

        @DisplayName("변환 가능한 모듈 타입이면 API를 생성한다.")
        @Test
        void createModuleApi() {
            ModuleResponseDto<?> moduleResponseDto = ModuleResponseDto.of(
                    REAL_TIME_ARCHIVE, INDEX, REAL_TIME_ARCHIVE_RESPONSE);
            ModuleResponseDto<?> homeModuleResponse = homeModuleApiFactory.from(moduleResponseDto);

            ArchivesResponse archivesResponse = (ArchivesResponse) homeModuleResponse.getData();

            assertAll(
                    () -> assertThat(homeModuleResponse.getModuleName()).isEqualTo("REAL_TIME_ARCHIVE"),
                    () -> assertThat(archivesResponse.getTopTitle()).isEqualTo(TOP_TITLE),
                    () -> assertThat(archivesResponse.getArchives().get(0).getMetaData().getDimColor()).isEqualTo(
                            DIM_COLOR)
            );
        }

        @DisplayName("변환 가능하지 않은 모듈 타입이면 입력한 DTO를 그대로 반환한다.")
        @Test
        void returnModule() {
            ModuleResponseDto<?> moduleResponseDto = ModuleResponseDto.of(
                    ModuleType.SWIPE_ITEM, INDEX, REAL_TIME_ARCHIVE_RESPONSE);
            ModuleResponseDto<?> homeModuleResponse = homeModuleApiFactory.from(moduleResponseDto);

            RealTimeArchiveResponse realTimeArchiveResponse = (RealTimeArchiveResponse) homeModuleResponse.getData();

            assertAll(
                    () -> assertThat(realTimeArchiveResponse.getArchives().get(0).getTitle()).isEqualTo(TITLE),
                    () -> assertThat(realTimeArchiveResponse.getTopTitle()).isEqualTo(TOP_TITLE)
            );
        }
    }
}
