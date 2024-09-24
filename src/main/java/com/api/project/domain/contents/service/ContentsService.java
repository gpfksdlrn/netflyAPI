package com.api.project.domain.contents.service;

import com.api.project.CommonResponse;
import com.api.project.common.entity.*;
import com.api.project.common.entity.ContentsType;
import com.api.project.common.enums.*;
import com.api.project.common.exception.CustomException;
import com.api.project.common.repository.*;
import com.api.project.common.utils.IdGenerator;
import com.api.project.domain.contents.controller.req.*;
import com.api.project.domain.contents.controller.res.*;
import com.api.project.domain.contents.query.ContentsCustomRepository;
import com.api.project.domain.contents.query.dto.ContentsListQueryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class ContentsService {
    private final VersionHistoryRepository versionHistoryRepository;
    private final UploadFileRepository uploadFileRepository;
    private final StorageRepository storageRepository;
    private final MemberRepository memberRepository;
    private final ContentsTypeRepository contentsTypeRepository;
    private final ContentsVersionRepository contentsVersionRepository;
    private final ContentsRepository contentsRepository;
    private final WorkStatusRepository workStatusRepository;
    private final ContentsCustomRepository contentsCustomRepository;

    @ReadOnlyProperty
    public CommonResponse getContentsList(ContentsListSearchReqDTO dto) {
        List<ContentsResDTO> res = new ArrayList<>();
        List<ContentsListQueryDTO> contentsList = contentsCustomRepository.selectContentsList(dto);

        for (ContentsListQueryDTO contents : contentsList) {
            res.add(ContentsResDTO.builder()
                    .contentNo(contents.getCtSeq())
                    .ctTypeName(contents.getCtTypeName())
                    .ctId(contents.getCtId())
                    .ctName(contents.getCtName())
                    .ctVersion(contents.getCvVersion())
                    .insertDt(contents.getInsertDt())
                    .memberId(contents.getMemberId())
                    .cvStat(contents.getWsName())
                    .cvPercent(contents.getCvPercent())
                    .isClear(contents.getWsCode())
                    .cvOrder(contents.getCvOrder())
                    .build());
        }

        return CommonResponse.responseSuccess(res);
    }

    @ReadOnlyProperty
    public CommonResponse getStatusCodeList() {
        List<StatusCodeResDTO> res = new ArrayList<>();
        List<WorkStatus> statusList = workStatusRepository.findAllByIsDelete(IsYn.N);

        for (WorkStatus workStatus : statusList) {
            res.add(StatusCodeResDTO.builder()
                    .code(workStatus.getWsCode())
                    .codeName(workStatus.getWsName())
                    .build());
        }

        return CommonResponse.responseSuccess(res);
    }

    @ReadOnlyProperty
    public CommonResponse getTotalPageCount() {
        int size = 10;  // 페이지당 컨텐츠 수

        // 전체 컨텐츠의 수
        long totalCount = contentsRepository.count();

        // 전체 페이지 수를 계산
        int totalPages = (int) Math.ceil((double) totalCount / size);

        TotalPageCountResDTO dto = TotalPageCountResDTO.builder()
                .totalPageCount(totalPages)
                .build();

        return CommonResponse.responseSuccess(dto);
    }

    public CommonResponse updateContentsOrder(UpdateContentsOrderReqDTO dto) {
        Contents contents = contentsRepository.findById(dto.getCtSeq())
                .orElseThrow(() -> new CustomException("콘텐츠를 찾을 수 없습니다."));

        ContentsVersion contentsVersion = contentsVersionRepository.findByContentsAndCvIsLastVersion(contents,IsYn.Y)
                .orElseThrow(() -> new CustomException("해당 콘텐츠의 마지막 버전을 찾을 수 없습니다."));

        contentsVersion.ChangeOrder(dto.getCvOrder());
        return CommonResponse.responseSuccess();
    }

    public CommonResponse getTypeList() {
        List<ContentsTypeResDTO> res = new ArrayList<>();
        List<ContentsType> contentsTypeList = contentsTypeRepository.findAllByIsDelete(IsYn.N);

        for(ContentsType contentsType : contentsTypeList) {
            res.add(ContentsTypeResDTO.builder()
                    .typeSeq(contentsType.getId())
                    .ctTypeName(contentsType.getCtTypeName().toString())
                    .build());
        }
        return CommonResponse.responseSuccess(res);
    }

    public CommonResponse createContents(CreateContentsReqDTO dto) {
        // 1. tb_cntents 테이블 저장
        ContentsType contentsType = contentsTypeRepository.findByIdAndIsDelete(dto.getCtTypeSeq(), IsYn.N)
                .orElseThrow(() -> new CustomException(ExceptionEnum.CONTENTS_TYPE_NONE_EXISTS.getMessage()));

        Member member = memberRepository.findByIdAndIsDelete(dto.getMemberSeq(), IsYn.N)
                .orElseThrow(() -> new CustomException(ExceptionEnum.MEMBER_NONE_EXISTS.getMessage()));

        // 중복 컨텐츠ID 조회
        long ctByCtIdCount = contentsRepository.countByCtIdAndIsDelete(dto.getCtId(), IsYn.N);
        if(ctByCtIdCount > 0 ){
            return CommonResponse.responseFail(ExceptionEnum.CONTENTS_ALREADY_EXISTS.getMessage());
        }

        Contents newContents = Contents.builder()
                .ctId(dto.getCtId())
                .contentsType(contentsType)
                .ctName(dto.getCtName())
                .member(member)
                .insertDt(LocalDateTime.now())
                .updateDt(LocalDateTime.now())
                .isDelete(IsYn.N)
                .build();

        Contents res = contentsRepository.save(newContents);

        // 2. tb_contents_version 저장
        String cvId = IdGenerator.random_id(res.getId());
        Contents contents = contentsRepository.findById(res.getId())
                .orElseThrow(() -> new CustomException(ExceptionEnum.CONTENTS_CREATE_ERROR.getMessage()));

        long cvByCvIdCount = contentsVersionRepository.countByCvIdAndIsDelete(cvId, IsYn.N);
        if(cvByCvIdCount > 0 ){
            throw new CustomException(ExceptionEnum.CONTENTS_VERSION_ALREADY_EXISTS.getMessage());
        }

        WorkStatus ws = workStatusRepository.findByIdAndIsDelete(1, IsYn.N);
        ContentsVersion newContentsVersion = ContentsVersion.builder()
                .contents(contents)
                .cvId(cvId)
                .cvVodName(dto.getCtName())
                .cvVersion(1)
                .workStatus(ws)
                .cvPercent(0)
                .cvOrder((short) 0)
                .cvIsLastVersion(IsYn.Y)
                .cvComment(Objects.equals(dto.getCvComment(), "") ? null : dto.getCvComment())
                .insertDt(LocalDateTime.now())
                .updateDt(LocalDateTime.now())
                .isDelete(IsYn.N)
                .build();

        contentsVersionRepository.save(newContentsVersion);

        return CommonResponse.responseSuccess(res);
    }

    public CommonResponse contentsSearch(ContentsSearchReqDTO dto) {
        // 컨텐츠ID로 검색
        Contents contents = contentsRepository.findByCtIdAndIsDelete(dto.getKeywords(), IsYn.N)
                .orElseThrow(() -> new CustomException(ExceptionEnum.CONTENTS_NONE_EXISTS.getMessage()));

        ContentsVersion contentsVersion = contentsVersionRepository.findByContents_IdAndCvIsLastVersionAndIsDelete(contents.getId(), IsYn.Y, IsYn.N)
                .orElseThrow(() -> new CustomException(ExceptionEnum.CONTENTS_NONE_EXISTS.getMessage()));

        ContentsSearchResDTO res = ContentsSearchResDTO.builder()
                .contentNo(contents.getId())
                .ctId(contents.getCtId())
                .ctName(contents.getCtName())
                .ctTypeName(contents.getContentsType().getCtTypeName().toString())
                .cvId(contentsVersion.getId())
                .build();
        return CommonResponse.responseSuccess(res);
    }

    public CommonResponse s3Config(S3BucketNameReqDTO dto) {
        Storage storage = storageRepository.findByStBucketAndIsDelete(dto.getBucketName(), IsYn.N)
                .orElseThrow(() -> new CustomException(ExceptionEnum.S3_NONE_EXISTS.getMessage()));

        S3BucketResDTO res = S3BucketResDTO.builder()
                .stRegion(storage.getStRegion())
                .stPoolId(storage.getStPoolId())
                .build();
        return CommonResponse.responseSuccess(res);
    }

    @Transactional
    public synchronized CommonResponse insertUploadFIle(UploadFileInfoReqDTO dto) {
        Storage storage = storageRepository.findByStBucketAndIsDelete(dto.getStName(), IsYn.N)
                .orElseThrow(() -> new CustomException(ExceptionEnum.S3_NONE_EXISTS.getMessage()));

        ContentsVersion contentsVersion = contentsVersionRepository.findByIdAndIsDelete(dto.getCvSeq(), IsYn.N)
                .orElseThrow(() -> new CustomException(ExceptionEnum.CONTENTS_NONE_EXISTS.getMessage()));

        Member member = memberRepository.findByIdAndIsDelete(dto.getMemberSeq(), IsYn.N)
                .orElseThrow(() -> new CustomException(ExceptionEnum.MEMBER_NONE_EXISTS.getMessage()));

        UploadFile newUploadFile = UploadFile.builder()
                .storage(storage)
                .contentsVersion(contentsVersion)
                .member(member)
                .ufPercent(dto.getUfPercent())
                .ufOriginName(dto.getUfOriginName())
                .ufHashName(dto.getUfHashName())
                .ufPath(dto.getUfPath())
                .ufType(UploadFileType.ORIGIN)
                .ufSize(dto.getUfSize())
                .ufExt(dto.getUfExt())
                .insertDt(LocalDateTime.now())
                .updateDt(LocalDateTime.now())
                .isDelete(IsYn.N)
                .build();

        UploadFile uploadRes = uploadFileRepository.save(newUploadFile);

        VersionHistory newHistory = VersionHistory.builder()
                .contentsVersion(contentsVersion)
                .vhWorkname(StatusCode.UI.getValue())
                .vhWorkStatus(StatusCode.UI.getKey())
                .insertDt(LocalDateTime.now())
                .updateDt(LocalDateTime.now())
                .isDelete(IsYn.N)
                .build();

        versionHistoryRepository.save(newHistory);
        return CommonResponse.responseSuccess(uploadRes);
    }

    public CommonResponse updateUploadFile(UploadFileInfoReqDTO dto) {
        Optional<UploadFile> optionalUploadFile = Optional.ofNullable(uploadFileRepository.findByContentsVersion_Id(dto.getCvSeq()));

        if(optionalUploadFile.isPresent()) {
            // 엔티티를 찾았을 경우, 값을 업데이트하고 저장
            UploadFile uploadFile = optionalUploadFile.get();
            uploadFile.setUfPercent(dto.getUfPercent());
            uploadFileRepository.save(uploadFile);

            // 파일 업로드가 100 퍼센트일 때
            if(dto.getUfPercent() == 100){
                ContentsVersion contentsVersion = contentsVersionRepository.findByIdAndIsDelete(dto.getCvSeq(), IsYn.N)
                        .orElseThrow(() -> new CustomException(ExceptionEnum.CONTENTS_NONE_EXISTS.getMessage()));

                VersionHistory newHistory = VersionHistory.builder()
                        .contentsVersion(contentsVersion)
                        .vhWorkname(StatusCode.US.getValue())
                        .vhWorkStatus(StatusCode.US.getKey())
                        .insertDt(LocalDateTime.now())
                        .updateDt(LocalDateTime.now())
                        .isDelete(IsYn.N)
                        .build();

                versionHistoryRepository.save(newHistory);
            }

            return CommonResponse.responseSuccess(uploadFile);
        } else {
            return CommonResponse.responseFail(ExceptionEnum.CONTENTS_NONE_EXISTS.getMessage());
        }
    }
}
