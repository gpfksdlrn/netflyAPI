package com.api.project.common.entity;

import com.api.project.common.enums.ContentsTypeEnum;
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
@Table(name = "tb_contents_type")
public class ContentsType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ct_type_seq", nullable = false)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ct_type_name", nullable = false)
    private ContentsTypeEnum ctTypeName;

    @Column(name = "insert_dt", nullable = false)
    private LocalDateTime insertDt;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_delete", nullable = false)
    private IsYn isDelete;
}
