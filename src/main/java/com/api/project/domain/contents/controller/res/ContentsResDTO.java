package com.api.project.domain.contents.controller.res;

import com.api.project.common.enums.StatusCode;
import lombok.Builder;

@Builder
public record ContentsResDTO(
        long contentNo,
        String ctTypeName,
        String ctId,
        String ctName,
        int ctVersion,
        String insertDt,
        String memberId,
        String cvStat,
        int cvPercent,
        StatusCode isClear,
        int cvOrder
) {}
