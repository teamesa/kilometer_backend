package com.kilometer.domain.homeModules;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class ModuleParamGenerator {

    public static ModuleParamDto from(Long userId, Object data) {
        LocalDateTime time = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        return ModuleParamDto.of(time, userId, data);
    }

}
