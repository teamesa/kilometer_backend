package com.kilometer.domain.homeModules.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleUpdateRequestList {

    private List<ModuleUpdateRequest> moduleList;
}
