package com.api.project.domain.contents.query.dto;

import com.api.project.common.enums.StatusCode;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ContentsListQueryDTO {
    long ctSeq;
    String ctTypeName;
    String ctId;
    String ctName;
    int cvVersion;
    String insertDt;
    String memberId;
    String wsName;
    int cvPercent;
    StatusCode wsCode;
    int cvOrder;

    @QueryProjection
    public ContentsListQueryDTO(long ctSeq, String ctTypeName, String ctId, String ctName, int cvVersion, String insertDt, String memberId, String wsName, int cvPercent, StatusCode wsCode, int cvOrder) {
        this.ctSeq = ctSeq;
        this.ctTypeName = ctTypeName;
        this.ctId = ctId;
        this.ctName = ctName;
        this.cvVersion = cvVersion;
        this.insertDt = insertDt;
        this.memberId = memberId;
        this.wsName = wsName;
        this.cvPercent = cvPercent;
        this.wsCode = wsCode;
        this.cvOrder = cvOrder;
    }
}
