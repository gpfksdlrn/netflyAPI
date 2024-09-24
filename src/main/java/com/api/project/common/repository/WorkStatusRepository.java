package com.api.project.common.repository;

import com.api.project.common.entity.WorkStatus;
import com.api.project.common.enums.IsYn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkStatusRepository extends JpaRepository<WorkStatus, Long> {
    List<WorkStatus> findAllByIsDelete(IsYn isYn);

    WorkStatus findByIdAndIsDelete(long id, IsYn isDelete);
}