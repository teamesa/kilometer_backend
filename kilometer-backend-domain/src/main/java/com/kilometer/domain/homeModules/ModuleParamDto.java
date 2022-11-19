package com.kilometer.domain.homeModules;

import com.kilometer.domain.homeModules.modules.dto.ModuleDto;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ModuleParamDto {

    private final LocalDateTime time;
    private final Long userId;
    private final ModuleDto moduleDto;

    public static ModuleParamDto of(LocalDateTime time, Long userId, ModuleDto moduleDto) {
        return new ModuleParamDto(time,userId,moduleDto);
    }
}
