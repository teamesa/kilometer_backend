package com.kilometer.domain.home.keyVisual;

import com.kilometer.domain.home.keyVisual.dto.KeyVisualResponse;
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
    private String title;
    private String subtitle;
    private String homepageUrl;

    private String updateAccount;
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    public KeyVisualResponse makeResponse() {
        return KeyVisualResponse.builder()
                .id(this.id)
                .imageUrl(this.imageUrl)
                .title(this.title)
                .subtitle(this.subtitle)
                .homepageUrl(this.homepageUrl)
                .updateAccount(this.updateAccount)
                .updateAt(this.updatedAt)
                .build();
    }
}
