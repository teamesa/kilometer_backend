package com.kilometer.backend.util;

import static com.kilometer.common.Fixture.HOME_MODULE_RESPONSE_DTO;
import static org.assertj.core.api.Assertions.assertThat;

import com.kilometer.domain.homeModules.HomeApiResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ApiGenerator 는 ")
class ApiGeneratorTest {

    @DisplayName("홈 모듈 API를 만들 수 있어야 한다.")
    @Test
    void generateHomeModuleApi() {
        HomeApiResponse homeApiResponse = HomeApiResponse.from(List.of(HOME_MODULE_RESPONSE_DTO));

        assertThat(homeApiResponse.getModules().size()).isEqualTo(1);
    }
}
