package com.api.project.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWorkStatus is a Querydsl query type for WorkStatus
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWorkStatus extends EntityPathBase<WorkStatus> {

    private static final long serialVersionUID = -784932095L;

    public static final QWorkStatus workStatus = new QWorkStatus("workStatus");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> insertDt = createDateTime("insertDt", java.time.LocalDateTime.class);

    public final EnumPath<com.api.project.common.enums.IsYn> isDelete = createEnum("isDelete", com.api.project.common.enums.IsYn.class);

    public final StringPath wsCode = createString("wsCode");

    public final StringPath wsName = createString("wsName");

    public QWorkStatus(String variable) {
        super(WorkStatus.class, forVariable(variable));
    }

    public QWorkStatus(Path<? extends WorkStatus> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWorkStatus(PathMetadata metadata) {
        super(WorkStatus.class, metadata);
    }

}

