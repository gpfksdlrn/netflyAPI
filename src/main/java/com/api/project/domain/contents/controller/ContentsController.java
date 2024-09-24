package com.api.project.domain.contents.controller;

import com.api.project.CommonResponse;
import com.api.project.common.enums.ExceptionEnum;
import com.api.project.common.utils.LogUtil;
import com.api.project.domain.contents.controller.req.*;
import com.api.project.domain.contents.service.ContentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ContentsController.Url.ROOT)
@RestController
@RequiredArgsConstructor
public class ContentsController {
    private final ContentsService service;
    private final String className = "ContentsController";

    public static class Url {
        public static final String ROOT = "/v1/api/contents";
        public static final String CONTENTS_LIST = "/list";
        public static final String CONTENTS_STATUSCODE = "/statusCode";
        public static final String CONTENTS_TOTALPAGE = "/totalPage";
        public static final String CONTENTS_UPDATEORDER = "/update/order";
        public static final String CONTENTS_TYPE = "/type";
        public static final String CONTENTS_CREATE = "/create";
        public static final String CONTENTS_SEARCHCONTENTS = "/search";
        public static final String CONTENTS_S3CONFIG = "/s3Config";
        public static final String CONTENTS_INSERTUPLOAD = "/insertUploadFile";
        public static final String CONTENTS_UPDATEUPLOAD = "/updateUploadFile";
    }

    @GetMapping(Url.CONTENTS_LIST)
    public CommonResponse contentsList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "") String searchText,
            @RequestParam(defaultValue = "") String contentType,
            @RequestParam(defaultValue = "") String status
    ) {
        try {
            ContentsListSearchReqDTO dto = new ContentsListSearchReqDTO(page, size, search, searchText, contentType, status);
            return service.getContentsList(dto);
        } catch (Exception e) {
            LogUtil.sendError(className, e.toString(), e);
            return CommonResponse.responseFail("리스팅 실패");
        }
    }

    @GetMapping(Url.CONTENTS_STATUSCODE)
    public CommonResponse statusCodeList() {
        try {
            return service.getStatusCodeList();
        } catch (Exception e) {
            LogUtil.sendError(className, e.toString(), e);
            return CommonResponse.responseFail("StatusCode 리스팅 실패");
        }
    }

    // 컨텐츠 리스트 총 페이지 수 조회
    @GetMapping(Url.CONTENTS_TOTALPAGE)
    public CommonResponse totalPageCount() {
        try {
            return service.getTotalPageCount();
        } catch (Exception e) {
            LogUtil.sendError(className, e.toString(), e);
            return CommonResponse.responseFail("totalPageCount 조회 실패");
        }
    }

    // 컨텐츠 우선순위 변경
    @PostMapping(Url.CONTENTS_UPDATEORDER)
    public CommonResponse updateContentsOrder(@RequestBody UpdateContentsOrderReqDTO dto) {
        try {
            return service.updateContentsOrder(dto);
        } catch (Exception e) {
            LogUtil.sendError(className, e.toString(), e);
            return CommonResponse.responseFail("contentOrder 변경 실패");
        }
    }

    // 컨텐츠 분류 조회
    @GetMapping(Url.CONTENTS_TYPE)
    public CommonResponse typeList() {
        try {
            return service.getTypeList();
        } catch (Exception e) {
            LogUtil.sendError(className, e.toString(), e);
            return CommonResponse.responseFail("컨텐츠 분류 조회 실패");
        }
    }

    // 컨텐츠 등록
    @PostMapping(Url.CONTENTS_CREATE)
    public CommonResponse contentsCreate(@RequestBody CreateContentsReqDTO dto) {
        try {
            return service.createContents(dto);
        } catch (Exception e) {
            LogUtil.sendError(className, e.toString(), e);
            return CommonResponse.responseFail(ExceptionEnum.CONTENTS_CREATE_ERROR.getMessage());
        }
    }

    // 컨텐츠 검색(파일업로드)
    @PostMapping(Url.CONTENTS_SEARCHCONTENTS)
    public CommonResponse contentsSearch(@RequestBody ContentsSearchReqDTO dto) {
        try {
            return service.contentsSearch(dto);
        } catch (Exception e) {
            LogUtil.sendError(className, e.toString(), e);
            return CommonResponse.responseFail(ExceptionEnum.CONTENTS_NONE_EXISTS.getMessage());
        }
    }

    // S3 Config 정보 조회
    @PostMapping(Url.CONTENTS_S3CONFIG)
    public CommonResponse s3Config(@RequestBody S3BucketNameReqDTO dto) {
        try {
            return service.s3Config(dto);
        } catch (Exception e) {
            LogUtil.sendError(className, e.toString(), e);
            return CommonResponse.responseFail(ExceptionEnum.S3_NONE_EXISTS.getMessage());
        }
    }

    // 업로드된 파일 DB 저장
    @PostMapping(Url.CONTENTS_INSERTUPLOAD)
    public CommonResponse insertUploadFIle(@RequestBody UploadFileInfoReqDTO dto) {
        try {
            return service.insertUploadFIle(dto);
        } catch (Exception e) {
            LogUtil.sendError(className, e.toString(), e);
            return CommonResponse.responseFail(ExceptionEnum.S3_NONE_EXISTS.getMessage());
        }
    }

    // 업로드 진행률 DB 저장
    @PostMapping(Url.CONTENTS_UPDATEUPLOAD)
    public CommonResponse updateUploadFIle(@RequestBody UploadFileInfoReqDTO dto) {
        try {
            return service.updateUploadFile(dto);
        } catch (Exception e) {
            LogUtil.sendError(className, e.toString(), e);
            return CommonResponse.responseFail(ExceptionEnum.S3_NONE_EXISTS.getMessage());
        }
    }
}