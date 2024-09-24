package com.api.project.domain.contents.controller.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateContentsOrderReqDTO {
    private long ctSeq;
    private short cvOrder;
}
