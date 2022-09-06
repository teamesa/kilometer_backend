package com.kilometer.domain.homeModules;

import com.kilometer.domain.backOfficeAccount.BackOfficeAccount;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private String moduleName;
    private String upperModuleTitle;
    private String lowerModuleTitle;
    private String extraData;

    @Builder.Default
    private boolean isDelete = false;
    @Builder.Default
    private LocalDateTime createAt = LocalDateTime.now();

    @ManyToOne
    private BackOfficeAccount account;
}
