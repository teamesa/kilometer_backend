package com.kilometer.domain.homeModules.dto;

import com.kilometer.domain.backOfficeAccount.BackOfficeAccount;
import com.kilometer.domain.homeModules.Module;
import com.kilometer.domain.homeModules.enumType.ModuleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleUpdateRequest {

    private Long id;
    private Integer exposureOrderNumber;
    private ModuleType moduleName;
    private String upperModuleTitle;
    private String lowerModuleTitle;
    private String extraData;

    public boolean isNotEmpty() {
        return id != null || exposureOrderNumber != null || !ModuleType.EMPTY.equals(moduleName)
                || StringUtils.hasText(upperModuleTitle) || StringUtils.hasText(lowerModuleTitle)
                || StringUtils.hasText(extraData);
    }

    public boolean isNotNull() {
        return id != null || exposureOrderNumber != null || moduleName != null
                || StringUtils.hasText(upperModuleTitle) || StringUtils.hasText(lowerModuleTitle)
                || StringUtils.hasText(extraData);
    }

    public boolean isNotDelete() {
        return id == null || exposureOrderNumber != null || moduleName != null
                || StringUtils.hasText(upperModuleTitle) || StringUtils.hasText(lowerModuleTitle)
                || StringUtils.hasText(extraData);
    }

    public Module makeModule(BackOfficeAccount account) {
        return Module.builder()
                .id(this.id)
                .exposureOrderNumber(this.exposureOrderNumber)
                .moduleName(this.moduleName)
                .upperModuleTitle(this.upperModuleTitle)
                .lowerModuleTitle(this.lowerModuleTitle)
                .extraData(this.extraData)
                .account(account)
                .build();
    }
}
