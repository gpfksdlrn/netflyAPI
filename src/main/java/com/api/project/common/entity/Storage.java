package com.api.project.common.entity;

import com.api.project.common.enums.IsYn;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_storage")
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "st_seq", nullable = false)
    private long id;

    @Column(name = "st_access_key", nullable = false)
    private String stAccessKey;

    @Column(name = "st_secret_key", nullable = false)
    private String stSecretKey;

    @Column(name = "st_bucket", nullable = false)
    private String stBucket;

    @Column(name = "st_default_domain", nullable = false)
    private String stDefaultDomain;

    @Column(name = "st_cf_domain", nullable = false)
    private String stCfDomain;

    @Column(name = "st_region", nullable = false)
    private String stRegion;

    @Column(name = "st_pool_id", nullable = false)
    private String stPoolId;

    @Column(name = "insert_dt", nullable = false)
    private LocalDateTime insertDt;

    @Column(name = "update_dt", nullable = false)
    private LocalDateTime updateDt;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_delete", nullable = false)
    private IsYn isDelete;
}
