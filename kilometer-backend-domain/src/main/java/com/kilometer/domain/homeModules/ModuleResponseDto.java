package com.kilometer.domain.homeModules;

import com.kilometer.domain.homeModules.enumType.ModuleType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModuleResponseDto<T> {

    private String moduleName;
    private int index;
    private T data;

    public static <T> ModuleResponseDto<T> of(ModuleType moduleName, int index, T data) {
        return new ModuleResponseDto<>(moduleName.toString(), index, data);
    }
}
