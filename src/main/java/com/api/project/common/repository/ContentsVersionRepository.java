package com.api.project.common.repository;

import com.api.project.common.entity.Contents;
import com.api.project.common.entity.ContentsVersion;
import com.api.project.common.enums.IsYn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContentsVersionRepository extends JpaRepository<ContentsVersion, Long> {
    Optional<ContentsVersion> findByContentsAndCvIsLastVersion(Contents contents, IsYn cvIsLastVersion);

    long countByCvIdAndIsDelete(String cvId, IsYn isDelete);

    Optional<ContentsVersion> findByContents_IdAndCvIsLastVersionAndIsDelete(long id, IsYn cvIsLastVersion, IsYn isDelete);

    Optional<ContentsVersion> findByIdAndIsDelete(long id, IsYn isDelete);
}