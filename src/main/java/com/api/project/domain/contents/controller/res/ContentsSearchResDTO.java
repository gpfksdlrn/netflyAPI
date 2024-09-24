package com.api.project.domain.contents.controller.res;

import lombok.Builder;

@Builder
public record ContentsSearchResDTO(
        long contentNo,
        String ctId,
        String ctName,
        String ctTypeName,
        long cvId
) {}
