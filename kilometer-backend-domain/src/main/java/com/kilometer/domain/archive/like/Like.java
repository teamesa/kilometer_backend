package com.kilometer.domain.archive.like;

import com.kilometer.domain.archive.ArchiveEntity;
import com.kilometer.domain.user.User;
import java.time.LocalDateTime;
import javax.persistence.Entity;
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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Builder
@Where(clause = "isDeleted=false")
@SQLDelete(sql = "UPDATE archive_like SET isDeleted=true where id=?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "archive_like")
public class Like {

    @Id
    @GeneratedValue
    private Long id;

    @Builder.Default
    private boolean isLiked = false;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder.Default
    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "likedUser")
    private User likedUser;

    @ManyToOne
    @JoinColumn(name = "likedArchive")
    private ArchiveEntity likedArchiveEntity;

    public Like changeIsLiked(boolean status) {
        this.isLiked = status;
        this.updatedAt = LocalDateTime.now();
        return this;
    }
}
