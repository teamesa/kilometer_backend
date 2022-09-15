package com.kilometer.domain.homeModules;

import com.kilometer.domain.backOfficeAccount.BackOfficeAccount;
import com.kilometer.domain.homeModules.dto.ModuleResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@Where(clause = "isDelete=false")
@SQLDelete(sql = "UPDATE module SET isDelete=true where id=?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "module")
public class Module {
    @Id @GeneratedValue
    private Long id;

    private int exposureOrderNumber;
    private String moduleName;
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

    public ModuleResponse makeResponse() {
        return ModuleResponse.builder()
                .id(this.id)
                .exposureOrderNumber(this.exposureOrderNumber)
                .moduleName(this.moduleName)
                .upperModuleTitle(this.upperModuleTitle)
                .lowerModuleTitle(this.lowerModuleTitle)
                .extraData(this.extraData)
                .createdAccount(this.account.getUsername())
                .createdAt(this.createdAt)
                .build();
    }
}
