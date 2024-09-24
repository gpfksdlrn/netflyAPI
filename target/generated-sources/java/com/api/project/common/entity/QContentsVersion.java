package com.api.project.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContentsVersion is a Querydsl query type for ContentsVersion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContentsVersion extends EntityPathBase<ContentsVersion> {

    private static final long serialVersionUID = 1118341760L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContentsVersion contentsVersion = new QContentsVersion("contentsVersion");

    public final QContents contents;

    public final StringPath cvComment = createString("cvComment");

    public final StringPath cvId = createString("cvId");

    public final EnumPath<com.api.project.common.enums.IsYn> cvIsLastVersion = createEnum("cvIsLastVersion", com.api.project.common.enums.IsYn.class);

    public final NumberPath<Short> cvOrder = createNumber("cvOrder", Short.class);

    public final NumberPath<Integer> cvPercent = createNumber("cvPercent", Integer.class);

    public final StringPath cvServer = createString("cvServer");

    public final NumberPath<Integer> cvVersion = createNumber("cvVersion", Integer.class);

    public final StringPath cvVodName = createString("cvVodName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> insertDt = createDateTime("insertDt", java.time.LocalDateTime.class);

    public final EnumPath<com.api.project.common.enums.IsYn> isDelete = createEnum("isDelete", com.api.project.common.enums.IsYn.class);

    public final DateTimePath<java.time.LocalDateTime> updateDt = createDateTime("updateDt", java.time.LocalDateTime.class);

    public final QWorkStatus workStatus;

    public QContentsVersion(String variable) {
        this(ContentsVersion.class, forVariable(variable), INITS);
    }

    public QContentsVersion(Path<? extends ContentsVersion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContentsVersion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContentsVersion(PathMetadata metadata, PathInits inits) {
        this(ContentsVersion.class, metadata, inits);
    }

    public QContentsVersion(Class<? extends ContentsVersion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.contents = inits.isInitialized("contents") ? new QContents(forProperty("contents"), inits.get("contents")) : null;
        this.workStatus = inits.isInitialized("workStatus") ? new QWorkStatus(forProperty("workStatus")) : null;
    }

}

