package com.api.project.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContents is a Querydsl query type for Contents
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContents extends EntityPathBase<Contents> {

    private static final long serialVersionUID = 2024971064L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContents contents = new QContents("contents");

    public final QContentsType contentsType;

    public final StringPath ctId = createString("ctId");

    public final StringPath ctName = createString("ctName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> insertDt = createDateTime("insertDt", java.time.LocalDateTime.class);

    public final EnumPath<com.api.project.common.enums.IsYn> isDelete = createEnum("isDelete", com.api.project.common.enums.IsYn.class);

    public final QMember member;

    public final DateTimePath<java.time.LocalDateTime> updateDt = createDateTime("updateDt", java.time.LocalDateTime.class);

    public QContents(String variable) {
        this(Contents.class, forVariable(variable), INITS);
    }

    public QContents(Path<? extends Contents> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContents(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContents(PathMetadata metadata, PathInits inits) {
        this(Contents.class, metadata, inits);
    }

    public QContents(Class<? extends Contents> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.contentsType = inits.isInitialized("contentsType") ? new QContentsType(forProperty("contentsType")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

