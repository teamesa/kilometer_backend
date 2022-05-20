package com.kilometer.domain.pick;

import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "pick")
public class Pick {
    @Id
    @GeneratedValue
    private Long id;

    private boolean isHearted;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "pickedUser")
    private User pickedUser;

    @ManyToOne
    @JoinColumn(name = "pickedItem")
    private ItemEntity pickedItem;

    public Pick changeIsHearted(boolean status) {
        this.isHearted = status;
        this.updatedAt = LocalDateTime.now();
        return this;
    }
}
