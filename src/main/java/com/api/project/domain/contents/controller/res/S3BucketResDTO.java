package com.api.project.domain.contents.controller.res;

import lombok.Builder;

@Builder
public record S3BucketResDTO(
    String stRegion,
    String stPoolId
) {}
