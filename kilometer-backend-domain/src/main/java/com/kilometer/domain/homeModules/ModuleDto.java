package com.kilometer.domain.homeModules;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModuleDto<T> {
    private String moduleName;
    private int index;
    private T data;

    public static <T> ModuleDto of(String moduleName, int index, T data){
        return new ModuleDto<>(moduleName, index, data);
    }
}
