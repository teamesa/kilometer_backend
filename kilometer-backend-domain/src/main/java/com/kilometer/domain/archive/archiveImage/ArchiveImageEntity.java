package com.kilometer.domain.archive.archiveImage;

import com.kilometer.domain.archive.ArchiveEntity;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Builder
@Entity
@Where(clause = "isDeleted=false")
@SQLDelete(sql = "UPDATE archive_image SET isDeleted=true where id=?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "archive_image")
public class ArchiveImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;


    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder.Default
    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "archive")
    private ArchiveEntity archiveEntity;

    public void initArchiveEntity(final ArchiveEntity archiveEntity) {
        this.archiveEntity = archiveEntity;
    }
}
