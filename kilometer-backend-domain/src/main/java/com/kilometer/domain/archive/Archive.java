package com.kilometer.domain.archive;

import com.kilometer.domain.archive.archiveImage.ArchiveImage;
import com.kilometer.domain.archive.dto.ArchiveInfo;
import com.kilometer.domain.archive.request.ArchiveRequest;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlace;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.user.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "archive")
public class Archive {

    @Id
    @GeneratedValue
    private Long id;

    @Builder.Default
    @Column(length = 1000)
    private String comment = null;

    private int starRating;

    @Builder.Default
    private int likeCount = 0;

    @Builder.Default
    private boolean isVisibleAtItem = true;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder.Default
    private boolean isDeleted = false;

    //======= 연관관계 =======
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item")
    private ItemEntity item;

    @OneToMany(mappedBy = "archive")
    @Builder.Default
    private List<UserVisitPlace> userVisitPlaces = new ArrayList<>();

    @OneToMany(mappedBy = "archive")
    @Builder.Default
    private List<ArchiveImage> archiveImages = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public void update(ArchiveRequest request) {
        this.comment = request.getComment();
        this.isVisibleAtItem = request.isVisibleAtItem();
        this.starRating = request.getStarRating();
    }

    public Archive plusLikeCount() {
        this.likeCount++;
        return this;
    }

    public Archive minusLikeCount() {
        this.likeCount = Math.max(this.likeCount - 1, 0);
        return this;
    }
}
