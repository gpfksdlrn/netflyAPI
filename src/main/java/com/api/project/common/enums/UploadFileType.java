package com.api.project.common.enums;

import lombok.Getter;

@Getter
public enum UploadFileType {
    ORIGIN("ORIGIN"),
    _1080("1080"),
    _720("720"),
    _480("480"),
    _360("360");

    private final String value;

    UploadFileType(String value) {
        this.value = value;
    }

}