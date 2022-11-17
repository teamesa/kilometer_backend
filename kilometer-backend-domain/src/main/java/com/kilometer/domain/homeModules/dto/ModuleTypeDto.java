package com.kilometer.domain.homeModules.dto;

import com.kilometer.domain.homeModules.enumType.ModuleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ModuleTypeDto {

    private ModuleType key;
    private final String description;
    private final String frontName;

    public static ModuleTypeDto of(ModuleType key, String description, String frontName) {
        return ModuleTypeDto.builder()
                .key(key)
                .description(description)
                .frontName(frontName)
                .build();
    }
}
