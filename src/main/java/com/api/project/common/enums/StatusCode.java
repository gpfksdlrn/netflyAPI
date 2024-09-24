package com.api.project.common.enums;

import lombok.Getter;

@Getter
public enum StatusCode {
    UW("UW","업로드 대기"),
    UI("UI","업로드 중"),
    US("US","업로드 성공"),
    UF("UF","업로드 실패"),
    MW("MW","미디어_인포_대기"),
    MI("MI","미디어 인포 중"),
    MS("MS","미디어 인포 성공"),
    MF("MF","미디어 인포 실패"),
    EW("EW","인코딩 대기"),
    EI("EI","인코딩 중"),
    ES("ES","인코딩 성공"),
    EF("EF","인코딩 실패"),
    SW("SW","검수대기"),
    SS("SS","검수완료");

    private final String key;
    private final String value;

    StatusCode(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
