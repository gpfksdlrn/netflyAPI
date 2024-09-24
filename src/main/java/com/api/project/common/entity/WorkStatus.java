package com.api.project.common.entity;

import com.api.project.common.enums.IsYn;
import com.api.project.common.enums.StatusCode;
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
@Table(name = "tb_work_status")
public class WorkStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ws_seq", nullable = false)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ws_code", nullable = false)
    private StatusCode wsCode;

    @Column(name = "ws_name", nullable = false)
    private String wsName;

    @Column(name = "insert_dt", nullable = false)
    private LocalDateTime insertDt;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_delete", nullable = false)
    private IsYn isDelete;
}
