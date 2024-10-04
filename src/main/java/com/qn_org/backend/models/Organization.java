package com.qn_org.backend.models;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@Entity
@RequiredArgsConstructor
@Table(name = "ORGANIZATION")
public class Organization {

    @Id
    @Column(name = "ORG_ID", nullable = false, unique = true)
    private String orgId;

    @Column(name = "ORG_NAME", nullable = false)
    private String orgName;

    @Column(name = "ORG_DESCRIPTION")
    private String orgDescription;

    @Column(name = "ORG_AVARTA", nullable = false)
    private String orgAvatar;

    @Column(name = "ORG_BACKGROUD", nullable = false)
    private String orgBackground;

    @Column(name = "MEMBERS", nullable = false)
    private int members = 1;

    @Column(name = "POSTS", nullable = false)
    private int posts = 0;

    @Column(name = "EVENTS", nullable = false)
    private int events = 0;

    @Column(name = "INS_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;

    @Column(name = "DEL_FLG", nullable = false)
    private boolean delFlg = false;

}
