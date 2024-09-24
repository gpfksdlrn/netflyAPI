package com.api.project.common.repository;

import com.api.project.common.entity.VersionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionHistoryRepository extends JpaRepository<VersionHistory, Long> {
}