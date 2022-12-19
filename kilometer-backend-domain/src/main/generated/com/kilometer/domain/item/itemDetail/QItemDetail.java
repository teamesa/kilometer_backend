package com.kilometer.domain.item.itemDetail;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemDetail is a Querydsl query type for ItemDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemDetail extends EntityPathBase<ItemDetail> {

    private static final long serialVersionUID = 1983881866L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemDetail itemDetail = new QItemDetail("itemDetail");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduce = createString("introduce");

    public final com.kilometer.domain.item.QItemEntity item;

    public QItemDetail(String variable) {
        this(ItemDetail.class, forVariable(variable), INITS);
    }

    public QItemDetail(Path<? extends ItemDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemDetail(PathMetadata metadata, PathInits inits) {
        this(ItemDetail.class, metadata, inits);
    }

    public QItemDetail(Class<? extends ItemDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new com.kilometer.domain.item.QItemEntity(forProperty("item"), inits.get("item")) : null;
    }

}

