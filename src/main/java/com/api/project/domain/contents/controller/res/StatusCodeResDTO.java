package com.api.project.domain.contents.controller.res;

import com.api.project.common.enums.StatusCode;
import lombok.Builder;

@Builder
public record StatusCodeResDTO(

        StatusCode code,
        String codeName
) {}
