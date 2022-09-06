package com.kilometer.domain.home.keyVisual;

import com.kilometer.domain.home.keyVisual.dto.KeyVisualResponse;
import com.kilometer.domain.home.keyVisual.dto.KeyVisualUpdateResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "key_visual")
public class KeyVisual {

    @Id
    @GeneratedValue
    private Long id;

    private String imageUrl;
    private String upperTitle;
    private String lowerTitle;
    private String linkUrl;

    private String createdAccount;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public KeyVisualResponse makeResponse() {
        return KeyVisualResponse.builder()
                .id(this.id)
                .imageUrl(this.imageUrl)
                .upperTitle(this.upperTitle)
                .lowerTitle(this.lowerTitle)
                .linkUrl(this.linkUrl)
                .createdAccount(this.createdAccount)
                .createdAt(this.createdAt)
                .build();
    }

    public void update(KeyVisualUpdateResponse keyVisualUpdateResponse, String createdAccount) {
        this.imageUrl = keyVisualUpdateResponse.getImageUrl();
        this.upperTitle = keyVisualUpdateResponse.getUpperTitle();
        this.lowerTitle = keyVisualUpdateResponse.getLowerTitle();
        this.linkUrl = keyVisualUpdateResponse.getLinkUrl();
        this.createdAccount = createdAccount;
        this.createdAt = LocalDateTime.now();
    }
}
