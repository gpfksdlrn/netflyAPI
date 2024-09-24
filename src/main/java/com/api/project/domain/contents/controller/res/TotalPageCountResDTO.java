package com.api.project.domain.contents.controller.res;

import lombok.Builder;

@Builder
public record TotalPageCountResDTO(
        long totalPageCount
) {}
