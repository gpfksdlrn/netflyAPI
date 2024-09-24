package com.api.project.common.repository;

import com.api.project.common.entity.Contents;
import com.api.project.common.enums.IsYn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContentsRepository extends JpaRepository<Contents, Long> {
    long countByCtIdAndIsDelete(String ctId, IsYn isDelete);

    Optional<Contents> findByCtIdAndIsDelete(String ctId, IsYn isDelete);
}