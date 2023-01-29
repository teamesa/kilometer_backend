package com.kilometer.domain.homeModules.modules.keyVisual;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QKeyVisual is a Querydsl query type for KeyVisual
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKeyVisual extends EntityPathBase<KeyVisual> {

    private static final long serialVersionUID = -1112217588L;

    public static final QKeyVisual keyVisual = new QKeyVisual("keyVisual");

    public final StringPath createdAccount = createString("createdAccount");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath linkUrl = createString("linkUrl");

    public final StringPath lowerTitle = createString("lowerTitle");

    public final StringPath upperTitle = createString("upperTitle");

    public QKeyVisual(String variable) {
        super(KeyVisual.class, forVariable(variable));
    }

    public QKeyVisual(Path<? extends KeyVisual> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKeyVisual(PathMetadata metadata) {
        super(KeyVisual.class, metadata);
    }

}

