package com.api.project.common.repository;

import com.api.project.common.entity.ContentsType;
import com.api.project.common.enums.IsYn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContentsTypeRepository extends JpaRepository<ContentsType, Long> {
    List<ContentsType> findAllByIsDelete(IsYn isYn);

    Optional<ContentsType> findByIdAndIsDelete(long id, IsYn isDelete);
}