package com.kilometer.domain.homeModules.modules;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QModule is a Querydsl query type for Module
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QModule extends EntityPathBase<Module> {

    private static final long serialVersionUID = 1954333360L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QModule module = new QModule("module");

    public final com.kilometer.domain.backOfficeAccount.QBackOfficeAccount account;

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> exposureOrderNumber = createNumber("exposureOrderNumber", Integer.class);

    public final StringPath extraData = createString("extraData");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDelete = createBoolean("isDelete");

    public final StringPath lowerModuleTitle = createString("lowerModuleTitle");

    public final EnumPath<com.kilometer.domain.homeModules.enumType.ModuleType> moduleName = createEnum("moduleName", com.kilometer.domain.homeModules.enumType.ModuleType.class);

    public final StringPath upperModuleTitle = createString("upperModuleTitle");

    public QModule(String variable) {
        this(Module.class, forVariable(variable), INITS);
    }

    public QModule(Path<? extends Module> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QModule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QModule(PathMetadata metadata, PathInits inits) {
        this(Module.class, metadata, inits);
    }

    public QModule(Class<? extends Module> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new com.kilometer.domain.backOfficeAccount.QBackOfficeAccount(forProperty("account")) : null;
    }

}

