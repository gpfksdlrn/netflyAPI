package com.api.project.domain.auth.enums;

public enum AuthExceptionMessage {
    SIGIN_NOT_FOUND_MESSAGE("아이디 또는 비밀번호를 다시 확인해주세요."),
    SIGNIN_ERROR_MESSAGE("로그인에 실패하였습니다."),
    TOKEN_REFRESH_ERROR_MESSAGE("토큰 재발급에 실패하였습니다."),
    NOT_FOUND_USER("회원 정보를 찾을 수 없습니다."),
    USER_NOT_FOUND_MESSAGE("회원 정보를 찾을 수 없습니다.");
    private final String MESSAGE;
    AuthExceptionMessage(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }
    @Override
    public String toString() {
        return MESSAGE;
    }
}

