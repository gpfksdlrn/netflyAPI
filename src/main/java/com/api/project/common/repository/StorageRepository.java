package com.api.project.common.repository;

import com.api.project.common.entity.Storage;
import com.api.project.common.enums.IsYn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, Long> {
    Optional<Storage> findByStBucketAndIsDelete(String stBucket, IsYn isDelete);

}