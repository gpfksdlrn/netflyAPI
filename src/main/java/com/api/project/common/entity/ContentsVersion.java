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
@Table(name = "tb_contents_version")
public class ContentsVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cv_seq", nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "ct_seq", nullable = false)
    private Contents contents;

    @Column(name = "cv_id", nullable = false)
    private String cvId;

    @Column(name = "cv_vod_name")
    private String cvVodName;

    @Column(name = "cv_version")
    private int cvVersion;

    @Column(name = "cv_server")
    private String cvServer;

    @ManyToOne
    @JoinColumn(name = "ws_seq", nullable = false)
    private WorkStatus workStatus;

    @Column(name = "cv_percent")
    private int cvPercent;

    @Column(name = "cv_order")
    private short cvOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "cv_is_last_version")
    private IsYn cvIsLastVersion;

    @Column(name = "cv_comment")
    private String cvComment;

    @Column(name = "insert_dt", nullable = false)
    private LocalDateTime insertDt;

    @Column(name = "update_dt", nullable = false)
    private LocalDateTime updateDt;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_delete", nullable = false)
    private IsYn isDelete;

    public void ChangeOrder(short order) {
        this.cvOrder = order;
    }
}
