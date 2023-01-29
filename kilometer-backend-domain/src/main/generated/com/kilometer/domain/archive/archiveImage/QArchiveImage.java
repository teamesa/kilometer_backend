package com.kilometer.domain.archive.archiveImage;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArchiveImage is a Querydsl query type for ArchiveImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArchiveImage extends EntityPathBase<ArchiveImage> {

    private static final long serialVersionUID = 1278316299L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArchiveImage archiveImage = new QArchiveImage("archiveImage");

    public final com.kilometer.domain.archive.QArchive archive;

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QArchiveImage(String variable) {
        this(ArchiveImage.class, forVariable(variable), INITS);
    }

    public QArchiveImage(Path<? extends ArchiveImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArchiveImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArchiveImage(PathMetadata metadata, PathInits inits) {
        this(ArchiveImage.class, metadata, inits);
    }

    public QArchiveImage(Class<? extends ArchiveImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.archive = inits.isInitialized("archive") ? new com.kilometer.domain.archive.QArchive(forProperty("archive"), inits.get("archive")) : null;
    }

}

