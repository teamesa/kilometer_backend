package com.kilometer.domain.archive;

import com.kilometer.domain.archive.archiveImage.ArchiveImageEntity;
import com.kilometer.domain.archive.request.ArchiveRequest;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlaceEntity;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.user.User;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Builder
@Where(clause = "isDeleted=false")
@SQLDelete(sql = "UPDATE archive SET isDeleted=true where id=?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "archive")
public class ArchiveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "archiveEntity")
    private List<ArchiveImageEntity> archiveImages;

    @OneToMany(mappedBy = "archiveEntity")
    private List<UserVisitPlaceEntity> userVisitPlaces;

    public void addArchiveImages(final List<ArchiveImageEntity> archiveImages) {
        this.archiveImages = archiveImages;
        archiveImages.forEach(archiveImage -> archiveImage.initArchiveEntity(this));
    }

    public void addUserVisitPlaces(final List<UserVisitPlaceEntity> userVisitPlaces) {
        this.userVisitPlaces = userVisitPlaces;
        userVisitPlaces.forEach(userVisitPlace -> userVisitPlace.initArchiveEntity(this));
    }

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

    public ArchiveEntity plusLikeCount() {
        this.likeCount++;
        return this;
    }

    public ArchiveEntity minusLikeCount() {
        this.likeCount = Math.max(this.likeCount - 1, 0);
        return this;
    }
}
