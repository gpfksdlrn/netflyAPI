package com.api.project.domain.contents.query;

import com.api.project.common.entity.*;
import com.api.project.common.enums.ContentsTypeEnum;
import com.api.project.common.enums.IsYn;
import com.api.project.common.enums.StatusCode;
import com.api.project.domain.contents.controller.req.ContentsListSearchReqDTO;
import com.api.project.domain.contents.query.dto.ContentsListQueryDTO;
import com.api.project.domain.contents.query.dto.QContentsListQueryDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ContentsCustomRepository {

    private final JPAQueryFactory queryFactory;

    QContents contents = QContents.contents;
    QContentsVersion contentsVersion = QContentsVersion.contentsVersion;
    QWorkStatus workStatus = QWorkStatus.workStatus;
    QMember member = QMember.member;
    QContentsType contentsType = QContentsType.contentsType;

    public List<ContentsListQueryDTO> selectContentsList(ContentsListSearchReqDTO dto) {
        BooleanBuilder where = new BooleanBuilder();

        // 공통조건
        where.and(contentsVersion.cvIsLastVersion.eq(IsYn.Y))
                .and(contentsVersion.isDelete.eq(IsYn.N))
                .and(contents.isDelete.eq(IsYn.N)
                );

        // 검색조건이 ct_id인지, ct_name인지
        if (dto.search().equals("id")) {
            where.and(contents.ctId.like("%"+dto.searchText()+"%"));
        } else if (dto.search().equals("name")) {
            where.and(contents.ctName.like("%"+dto.searchText()+"%"));
        }

        // 분류값이 들어 온 경우 조건 추가
        if(StringUtils.hasText(dto.contentType())) {
            where.and(contentsType.ctTypeName.eq(ContentsTypeEnum.valueOf(dto.contentType())));
        }

        // 인코딩 상태값이 들어 온 경우 조건 추가
        if(StringUtils.hasText(dto.status())) {
            where.and(workStatus.wsCode.eq(StatusCode.valueOf(dto.status())));
        }

        return queryFactory
                .select(
                        new QContentsListQueryDTO(
                                contents.id,
                                contentsType.ctTypeName.stringValue(),
                                contents.ctId,
                                contents.ctName,
                                contentsVersion.cvVersion,
                                Expressions.stringTemplate("DATE_FORMAT({0}, {1})", contents.insertDt, "%Y-%m-%d %H:%i:%s"),
                                member.memberId,
                                workStatus.wsName,
                                contentsVersion.cvPercent,
                                workStatus.wsCode,
                                contentsVersion.cvOrder.intValue()
                        ))
                .from(contentsVersion)
                .join(contentsVersion.contents, contents)
                .join(contentsVersion.workStatus, workStatus)
                .join(contents.member, member)
                .join(contents.contentsType, contentsType)
                .where(where)
                .orderBy(contents.id.desc())
                .offset((long) dto.page() * dto.size())
                .limit(dto.size())
                .fetch();
    }
}
