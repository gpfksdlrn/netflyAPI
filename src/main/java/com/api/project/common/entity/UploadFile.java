package com.api.project.common.entity;

import com.api.project.common.enums.IsYn;
import com.api.project.common.enums.UploadFileType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_upload_file")
public class UploadFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uf_seq")
    private long id;

    @ManyToOne
    @JoinColumn(name = "st_seq")
    private Storage storage;

    @ManyToOne
    @JoinColumn(name = "cv_seq")
    private ContentsVersion contentsVersion;

    @ManyToOne
    @JoinColumn(name = "member_seq")
    private Member member;

    @Setter
    @Column(name = "uf_percent", nullable = false)
    private int ufPercent;

    @Enumerated(EnumType.STRING)
    @Column(name = "uf_type", nullable = false)
    private UploadFileType ufType;

    @Column(name = "uf_origin_name", nullable = false)
    private String ufOriginName;

    @Column(name = "uf_hash_name", nullable = false)
    private String ufHashName;

    @Column(name = "uf_path", nullable = false)
    private String ufPath;

    @Column(name = "uf_ext", nullable = false)
    private String ufExt;

    @Column(name = "uf_size", nullable = false)
    private int ufSize;

    @Column(name = "insert_dt", nullable = false)
    private LocalDateTime insertDt;

    @Column(name = "update_dt", nullable = false)
    private LocalDateTime updateDt;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_delete", nullable = false)
    private IsYn isDelete;

}
