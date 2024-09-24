package com.api.project.domain.contents.controller.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UploadFileInfoReqDTO {
    private String stName;  // storage 이름
    private long cvSeq;
    private long memberSeq; // Member의 ID에 대한 정보
    private int ufPercent;
    private String ufOriginName;
    private String ufHashName;
    private String ufPath;
    private String ufExt;
    private int ufSize;
}
