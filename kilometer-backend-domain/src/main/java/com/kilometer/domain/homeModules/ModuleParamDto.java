package com.kilometer.domain.homeModules;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ModuleParamDto {

    private final LocalDateTime time;
    private final Long userId;
    private final Object data;

    public static ModuleParamDto of(LocalDateTime time, Long userId, Object data) {
        return new ModuleParamDto(time,userId,data);
    }

}
