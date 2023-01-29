package com.kilometer.domain.archive;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArchive is a Querydsl query type for Archive
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArchive extends EntityPathBase<Archive> {

    private static final long serialVersionUID = -41635585L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArchive archive = new QArchive("archive");

    public final StringPath comment = createString("comment");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final BooleanPath isVisibleAtItem = createBoolean("isVisibleAtItem");

    public final com.kilometer.domain.item.QItemEntity item;

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final NumberPath<Integer> starRating = createNumber("starRating", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final com.kilometer.domain.user.QUser user;

    public QArchive(String variable) {
        this(Archive.class, forVariable(variable), INITS);
    }

    public QArchive(Path<? extends Archive> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArchive(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArchive(PathMetadata metadata, PathInits inits) {
        this(Archive.class, metadata, inits);
    }

    public QArchive(Class<? extends Archive> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new com.kilometer.domain.item.QItemEntity(forProperty("item"), inits.get("item")) : null;
        this.user = inits.isInitialized("user") ? new com.kilometer.domain.user.QUser(forProperty("user")) : null;
    }

}

