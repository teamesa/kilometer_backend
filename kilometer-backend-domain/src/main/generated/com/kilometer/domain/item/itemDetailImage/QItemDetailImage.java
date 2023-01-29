package com.kilometer.domain.item.itemDetailImage;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemDetailImage is a Querydsl query type for ItemDetailImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemDetailImage extends EntityPathBase<ItemDetailImage> {

    private static final long serialVersionUID = 1355187228L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemDetailImage itemDetailImage = new QItemDetailImage("itemDetailImage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final com.kilometer.domain.item.QItemEntity item;

    public QItemDetailImage(String variable) {
        this(ItemDetailImage.class, forVariable(variable), INITS);
    }

    public QItemDetailImage(Path<? extends ItemDetailImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemDetailImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemDetailImage(PathMetadata metadata, PathInits inits) {
        this(ItemDetailImage.class, metadata, inits);
    }

    public QItemDetailImage(Class<? extends ItemDetailImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new com.kilometer.domain.item.QItemEntity(forProperty("item"), inits.get("item")) : null;
    }

}

