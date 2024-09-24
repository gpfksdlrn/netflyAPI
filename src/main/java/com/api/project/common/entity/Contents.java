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
@Table(name = "tb_contents")
public class Contents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ct_seq", nullable = false)
    private long id;

    @Column(name = "ct_id", nullable = false)
    private String ctId;

    @ManyToOne
    @JoinColumn(name = "ct_type_seq")
    private ContentsType contentsType;

    @Column(name = "ct_name", nullable = false)
    private String ctName;

    @ManyToOne
    @JoinColumn(name = "member_seq")
    private Member member;

    @Column(name = "insert_dt", nullable = false)
    private LocalDateTime insertDt;

    @Column(name = "update_dt", nullable = false)
    private LocalDateTime updateDt;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_delete", nullable = false)
    private IsYn isDelete;
}
