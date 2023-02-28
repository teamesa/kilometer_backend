package com.kilometer.domain.homeModules.modules.keyVisual;

import static com.kilometer.common.Fixture.LOWER_TITLE;
import static com.kilometer.common.Fixture.TOP_TITLE;
import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

import com.kilometer.common.annotation.SpringTestWithData;
import com.kilometer.domain.homeModules.ModuleParamDto;
import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.homeModules.modules.Module;
import com.kilometer.domain.homeModules.modules.ModuleHandler;
import com.kilometer.domain.homeModules.modules.dto.ModuleDto;
import com.kilometer.domain.homeModules.modules.keyVisual.dto.KeyVisualApiResponse;
import com.kilometer.domain.user.User;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("KeyVisual 은 ")
@SpringTestWithData
class KeyVisualHandlerTest {

    @Autowired
    ModuleHandler keyVisualHandler;

    @Autowired
    KeyVisualRepository keyVisualRepository;

    @DisplayName("모듈 타입이 KeyVisual 인지를 판별해야 한다.")
    @Test
    void support() {
        assertThat(keyVisualHandler.supports(ModuleType.KEY_VISUAL)).isTrue();
    }

    @DisplayName("KeyVisual 데이터를 조회할 때 ")
    @Nested
    class KeyVisualGenerationTest {

        @DisplayName("데이터가 유효하면 데이터를 반환한다.")
        @Test
        void generate() {
            saveKeyVisual();
            KeyVisualApiResponse keyVisualApiResponse = (KeyVisualApiResponse) generateKeyVisual().get();

            assertThat(keyVisualApiResponse.getKeyVisualDatas().size()).isEqualTo(1);
        }

        @DisplayName("데이터가 존재하지 않으면 데이터를 반환하지 않는다.")
        @Test
        void generateFailDueToEmptyData() {
            assertThat(generateKeyVisual().isEmpty()).isTrue();
        }

        private void saveKeyVisual() {
            KeyVisual keyVisual = KeyVisual.builder()
                    .linkUrl("link")
                    .upperTitle(TOP_TITLE)
                    .lowerTitle(LOWER_TITLE)
                    .imageUrl("imageUrl.com")
                    .build();
            keyVisualRepository.save(keyVisual);
        }

        private Optional<?> generateKeyVisual() {
            Module module = Module.builder()
                    .moduleName(ModuleType.KEY_VISUAL)
                    .upperModuleTitle(TOP_TITLE)
                    .lowerModuleTitle(LOWER_TITLE)
                    .build();
            ModuleParamDto moduleParamDto = ModuleParamDto.of(LocalDateTime.now(), null,
                    ModuleDto.from(module));
            return keyVisualHandler.generator(moduleParamDto);
        }
    }
}
