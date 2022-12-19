package com.kilometer.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemEntity is a Querydsl query type for ItemEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemEntity extends EntityPathBase<ItemEntity> {

    private static final long serialVersionUID = 516390970L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemEntity itemEntity = new QItemEntity("itemEntity");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final EnumPath<com.kilometer.domain.item.enumType.ExhibitionType> exhibitionType = createEnum("exhibitionType", com.kilometer.domain.item.enumType.ExhibitionType.class);

    public final EnumPath<com.kilometer.domain.item.enumType.ExposureType> exposureType = createEnum("exposureType", com.kilometer.domain.item.enumType.ExposureType.class);

    public final EnumPath<com.kilometer.domain.item.enumType.FeeType> feeType = createEnum("feeType", com.kilometer.domain.item.enumType.FeeType.class);

    public final StringPath homepageUrl = createString("homepageUrl");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final com.kilometer.domain.item.itemDetail.QItemDetail itemDetail;

    public final ListPath<com.kilometer.domain.item.itemDetailImage.ItemDetailImage, com.kilometer.domain.item.itemDetailImage.QItemDetailImage> itemDetailImages = this.<com.kilometer.domain.item.itemDetailImage.ItemDetailImage, com.kilometer.domain.item.itemDetailImage.QItemDetailImage>createList("itemDetailImages", com.kilometer.domain.item.itemDetailImage.ItemDetailImage.class, com.kilometer.domain.item.itemDetailImage.QItemDetailImage.class, PathInits.DIRECT2);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final StringPath listImageUrl = createString("listImageUrl");

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath operatingTime = createString("operatingTime");

    public final NumberPath<Integer> pickCount = createNumber("pickCount", Integer.class);

    public final StringPath placeName = createString("placeName");

    public final StringPath price = createString("price");

    public final StringPath regAccount = createString("regAccount");

    public final EnumPath<com.kilometer.domain.item.enumType.RegionType> regionType = createEnum("regionType", com.kilometer.domain.item.enumType.RegionType.class);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final StringPath thumbnailImageUrl = createString("thumbnailImageUrl");

    public final StringPath ticketUrl = createString("ticketUrl");

    public final StringPath title = createString("title");

    public final StringPath udtAccount = createString("udtAccount");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QItemEntity(String variable) {
        this(ItemEntity.class, forVariable(variable), INITS);
    }

    public QItemEntity(Path<? extends ItemEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemEntity(PathMetadata metadata, PathInits inits) {
        this(ItemEntity.class, metadata, inits);
    }

    public QItemEntity(Class<? extends ItemEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.itemDetail = inits.isInitialized("itemDetail") ? new com.kilometer.domain.item.itemDetail.QItemDetail(forProperty("itemDetail"), inits.get("itemDetail")) : null;
    }

}

