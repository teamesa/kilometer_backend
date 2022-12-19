package com.kilometer.domain.pick;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPick is a Querydsl query type for Pick
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPick extends EntityPathBase<Pick> {

    private static final long serialVersionUID = 691920439L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPick pick = new QPick("pick");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isHearted = createBoolean("isHearted");

    public final com.kilometer.domain.item.QItemEntity pickedItem;

    public final com.kilometer.domain.user.QUser pickedUser;

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QPick(String variable) {
        this(Pick.class, forVariable(variable), INITS);
    }

    public QPick(Path<? extends Pick> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPick(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPick(PathMetadata metadata, PathInits inits) {
        this(Pick.class, metadata, inits);
    }

    public QPick(Class<? extends Pick> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pickedItem = inits.isInitialized("pickedItem") ? new com.kilometer.domain.item.QItemEntity(forProperty("pickedItem"), inits.get("pickedItem")) : null;
        this.pickedUser = inits.isInitialized("pickedUser") ? new com.kilometer.domain.user.QUser(forProperty("pickedUser")) : null;
    }

}

