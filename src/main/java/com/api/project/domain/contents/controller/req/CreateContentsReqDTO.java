package com.api.project.domain.contents.controller.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateContentsReqDTO {
    private String ctId;
    private long ctTypeSeq; // ContentsType의 ID에 대한 정보
    private String ctName;
    private long memberSeq; // Member의 ID에 대한 정보
    private String cvComment;
}
