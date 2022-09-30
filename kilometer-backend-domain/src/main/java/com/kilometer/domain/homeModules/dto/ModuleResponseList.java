package com.kilometer.domain.homeModules.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ModuleResponseList {

    private List<ModuleResponse> moduleList;
}
