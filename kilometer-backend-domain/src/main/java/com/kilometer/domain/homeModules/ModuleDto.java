package com.kilometer.domain.homeModules;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModuleDto<T> {
    private String moduleName;
    private int index;
    private T data;
}
