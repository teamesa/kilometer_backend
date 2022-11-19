package com.kilometer.domain.homeModules.modules.dto;

import com.kilometer.domain.backOfficeAccount.BackOfficeAccount;
import com.kilometer.domain.homeModules.enums.ModuleType;
import com.kilometer.domain.homeModules.modules.Module;
import java.time.LocalDateTime;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ModuleDto {
    private Long id;
    private int exposureOrderNumber;
    private ModuleType moduleName;
    private String upperModuleTitle;
    private String lowerModuleTitle;
    private String extraData;
    private boolean isDelete;
    private LocalDateTime createdAt;

    public static ModuleDto from(Module module) {
        return ModuleDto.builder()
            .id(module.getId())
            .exposureOrderNumber(module.getExposureOrderNumber())
            .moduleName(module.getModuleName())
            .upperModuleTitle(module.getUpperModuleTitle())
            .lowerModuleTitle(module.getLowerModuleTitle())
            .extraData(module.getExtraData())
            .isDelete(module.isDelete())
            .createdAt(module.getCreatedAt())
            .build();
    }

}
