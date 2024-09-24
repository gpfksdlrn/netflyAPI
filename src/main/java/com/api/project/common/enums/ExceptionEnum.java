package com.api.project.common.enums;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
    CONTENTS_ALREADY_EXISTS("이미 존재하는 컨텐츠입니다."),
    CONTENTS_NONE_EXISTS("존재하지 않는 컨텐츠입니다."),
    CONTENTS_CREATE_ERROR("컨텐츠 등록 중 예외가 발생했습니다."),
    CONTENTS_TYPE_NONE_EXISTS("존재하지 않는 컨텐츠타입 입니다."),

    CONTENTS_VERSION_ALREADY_EXISTS("이미 존재하는 cvId가 있습니다."),

    MEMBER_NONE_EXISTS("존재하지 않는 멤버입니다."),

    S3_NONE_EXISTS("존재하지 않는 S3 버킷입니다."),
    ;
    private final String message;

    ExceptionEnum(String message) {
        this.message = message;
    }
}