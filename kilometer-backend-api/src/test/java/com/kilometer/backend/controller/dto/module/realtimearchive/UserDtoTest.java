package com.kilometer.backend.controller.dto.module.realtimearchive;

import static com.kilometer.common.Fixture.REAL_TIME_ARCHIVE_DTO;
import static com.kilometer.common.Fixture.USER_IMAGE_URL;
import static com.kilometer.common.Fixture.USER_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UserDto 는 ")
class UserDtoTest {

    @DisplayName("UserDto 를 생성해야 한다.")
    @Test
    void createUserDto() {
        UserDto userDto = UserDto.from(REAL_TIME_ARCHIVE_DTO);

        assertAll(
                () -> assertThat(userDto.getName()).isEqualTo(USER_NAME),
                () -> assertThat(userDto.getPhotoUrl()).isEqualTo(USER_IMAGE_URL)
        );
    }
}
