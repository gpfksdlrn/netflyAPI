package com.api.project.common.repository;

import com.api.project.common.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
    UploadFile findByContentsVersion_Id(long id);
}