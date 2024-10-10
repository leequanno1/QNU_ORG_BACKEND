package com.qn_org.backend.models;

import jakarta.persistence.*;
import lombok.*;

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
