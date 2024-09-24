package com.api.project.domain.contents.query.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.api.project.domain.contents.query.dto.QContentsListQueryDTO is a Querydsl Projection type for ContentsListQueryDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QContentsListQueryDTO extends ConstructorExpression<ContentsListQueryDTO> {

    private static final long serialVersionUID = 1513055908L;

    public QContentsListQueryDTO(com.querydsl.core.types.Expression<Long> ctSeq, com.querydsl.core.types.Expression<String> ctTypeName, com.querydsl.core.types.Expression<String> ctId, com.querydsl.core.types.Expression<String> ctName, com.querydsl.core.types.Expression<Integer> cvVersion, com.querydsl.core.types.Expression<String> insertDt, com.querydsl.core.types.Expression<String> memberId, com.querydsl.core.types.Expression<String> wsName, com.querydsl.core.types.Expression<Integer> cvPercent, com.querydsl.core.types.Expression<String> wsCode, com.querydsl.core.types.Expression<Integer> cvOrder) {
        super(ContentsListQueryDTO.class, new Class<?>[]{long.class, String.class, String.class, String.class, int.class, String.class, String.class, String.class, int.class, String.class, int.class}, ctSeq, ctTypeName, ctId, ctName, cvVersion, insertDt, memberId, wsName, cvPercent, wsCode, cvOrder);
    }

}

