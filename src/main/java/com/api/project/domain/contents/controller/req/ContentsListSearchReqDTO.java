package com.api.project.domain.contents.controller.req;

public record ContentsListSearchReqDTO(
        int page, // 페이지
        int size, // 게시물 수
        String search, // 검색조건
        String searchText, // 검색내용
        String contentType, // 분류
        String status
) {}
