package com.api.project.common.enums;

import lombok.Getter;

@Getter
public enum ContentsTypeEnum {
    MOVIE("MOVIE","영화"),  // 영화
    VOD("VOD","방송"),    // 방송
    CLIP("CLIP","클립");   // 클립

    private final String key;
    private final String value;

    ContentsTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }
}


