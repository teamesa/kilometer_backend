package com.kilometer.domain.archive.userVisitPlace;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserVisitPlace is a Querydsl query type for UserVisitPlace
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserVisitPlace extends EntityPathBase<UserVisitPlace> {

    private static final long serialVersionUID = -329611893L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserVisitPlace userVisitPlace = new QUserVisitPlace("userVisitPlace");

    public final StringPath address = createString("address");

    public final com.kilometer.domain.archive.QArchive archive;

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final StringPath placeName = createString("placeName");

    public final EnumPath<com.kilometer.domain.archive.PlaceType> placeType = createEnum("placeType", com.kilometer.domain.archive.PlaceType.class);

    public final StringPath roadAddress = createString("roadAddress");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QUserVisitPlace(String variable) {
        this(UserVisitPlace.class, forVariable(variable), INITS);
    }

    public QUserVisitPlace(Path<? extends UserVisitPlace> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserVisitPlace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserVisitPlace(PathMetadata metadata, PathInits inits) {
        this(UserVisitPlace.class, metadata, inits);
    }

    public QUserVisitPlace(Class<? extends UserVisitPlace> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.archive = inits.isInitialized("archive") ? new com.kilometer.domain.archive.QArchive(forProperty("archive"), inits.get("archive")) : null;
    }

}

