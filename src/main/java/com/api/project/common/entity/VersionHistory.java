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
@Table(name = "tb_version_history")
public class VersionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vh_seq", nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cv_seq", nullable = false)
    private ContentsVersion contentsVersion;

    @Column(name = "vh_workname", nullable = false)
    private String vhWorkname;

    @Column(name = "vh_work_status", nullable = false)
    private String vhWorkStatus;

    @Column(name = "vh_message")
    private String vhMessage;

    @Column(name = "insert_dt", nullable = false)
    private LocalDateTime insertDt;

    @Column(name = "update_dt", nullable = false)
    private LocalDateTime updateDt;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_delete", nullable = false)
    private IsYn isDelete;
}
