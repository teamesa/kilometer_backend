package com.kilometer.domain.homeModules.dto;

import com.kilometer.domain.homeModules.enumType.ModuleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ModuleResponse {

    private Long id;
    private Integer exposureOrderNumber;
    private ModuleType moduleName;
    private String upperModuleTitle;
    private String lowerModuleTitle;
    private String extraData;
    private String createdAccount;
    private LocalDateTime createdAt;
}
