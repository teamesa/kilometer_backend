package com.kilometer.domain.backOfficeAccount;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBackOfficeAccount is a Querydsl query type for BackOfficeAccount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBackOfficeAccount extends EntityPathBase<BackOfficeAccount> {

    private static final long serialVersionUID = -1217602993L;

    public static final QBackOfficeAccount backOfficeAccount = new QBackOfficeAccount("backOfficeAccount");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath password = createString("password");

    public final StringPath role = createString("role");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final StringPath username = createString("username");

    public QBackOfficeAccount(String variable) {
        super(BackOfficeAccount.class, forVariable(variable));
    }

    public QBackOfficeAccount(Path<? extends BackOfficeAccount> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBackOfficeAccount(PathMetadata metadata) {
        super(BackOfficeAccount.class, metadata);
    }

}

