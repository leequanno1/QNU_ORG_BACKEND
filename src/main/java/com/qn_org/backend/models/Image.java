package com.qn_org.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@Entity
@RequiredArgsConstructor
@Table(name = "IMAGE")
public class Image {
    @Id
    @Column(name = "IMAGE_ID", length = 255, nullable = false, unique = true)
    private String imageId;

    @Column(name = "PARENT_ID", length = 255, nullable = false)
    private String parentId;

    @Column(name = "IMAGE_URL", length = 255, nullable = false)
    private String imageUrl;

    @Column(name = "INS_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate = new Date();

    @Column(name = "DEL_FLG", nullable = false)
    private boolean delFlg = false;
}
