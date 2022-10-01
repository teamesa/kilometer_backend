package com.kilometer.domain.homeModules.modules;

import com.kilometer.domain.backOfficeAccount.BackOfficeAccount;
import com.kilometer.domain.homeModules.enums.ModuleType;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "module")
public class Module {
    @Id @GeneratedValue
    private Long id;

    private int exposureOrderNumber;
    @Enumerated(value = EnumType.STRING)
    private ModuleType moduleName;
    private String upperModuleTitle;
    private String lowerModuleTitle;
    private String extraData;

    @Builder.Default
    private boolean isDelete = false;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "createdAccount")
    private BackOfficeAccount account;
}
