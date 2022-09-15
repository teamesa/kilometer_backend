package com.kilometer.domain.homeModules.dto;

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
    private String moduleName;
    private String upperModuleTitle;
    private String lowerModuleTitle;
    private String extraData;
    private String createdAccount;
    private LocalDateTime createdAt;
}
