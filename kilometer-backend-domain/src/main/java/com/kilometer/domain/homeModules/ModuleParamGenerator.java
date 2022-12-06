package com.kilometer.domain.homeModules;

import com.kilometer.domain.homeModules.modules.dto.ModuleDto;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.stereotype.Component;

@Component
public class ModuleParamGenerator {

    public ModuleParamDto from(Long userId, ModuleDto moduleDto) {
        LocalDateTime time = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        return ModuleParamDto.of(time, userId, moduleDto);
    }

}
