package com.kilometer.domain.homeModules.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleUpdateResponse {

    private Long id;
    private Integer exposureOrderNumber;
    private String moduleName;
    private String upperModuleTitle;
    private String lowerModuleTitle;
    private String extraData;
}
