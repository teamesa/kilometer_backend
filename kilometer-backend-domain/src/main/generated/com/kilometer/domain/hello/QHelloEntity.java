package com.kilometer.domain.hello;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHelloEntity is a Querydsl query type for HelloEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHelloEntity extends EntityPathBase<HelloEntity> {

    private static final long serialVersionUID = -502252702L;

    public static final QHelloEntity helloEntity = new QHelloEntity("helloEntity");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memo = createString("memo");

    public final StringPath service = createString("service");

    public QHelloEntity(String variable) {
        super(HelloEntity.class, forVariable(variable));
    }

    public QHelloEntity(Path<? extends HelloEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHelloEntity(PathMetadata metadata) {
        super(HelloEntity.class, metadata);
    }

}

