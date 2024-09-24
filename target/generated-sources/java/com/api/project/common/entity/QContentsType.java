package com.api.project.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QContentsType is a Querydsl query type for ContentsType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContentsType extends EntityPathBase<ContentsType> {

    private static final long serialVersionUID = 1529496210L;

    public static final QContentsType contentsType = new QContentsType("contentsType");

    public final StringPath ctTypeName = createString("ctTypeName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> insertDt = createDateTime("insertDt", java.time.LocalDateTime.class);

    public final EnumPath<com.api.project.common.enums.IsYn> isDelete = createEnum("isDelete", com.api.project.common.enums.IsYn.class);

    public QContentsType(String variable) {
        super(ContentsType.class, forVariable(variable));
    }

    public QContentsType(Path<? extends ContentsType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QContentsType(PathMetadata metadata) {
        super(ContentsType.class, metadata);
    }

}

