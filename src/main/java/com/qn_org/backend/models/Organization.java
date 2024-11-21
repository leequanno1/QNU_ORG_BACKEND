package com.qn_org.backend.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "ORGANIZATION")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Organization {

    @Id
    @Column(name = "ORG_ID", nullable = false, unique = true)
    private String orgId;

    @Column(name = "ORG_NAME", nullable = false)
    private String orgName;

    @Column(name = "ORG_DESCRIPTION")
    private String orgDescription;

    @Column(name = "ORG_AVATAR")
    private String orgAvatar;

    @Column(name = "ORG_BACKGROUND")
    private String orgBackground;

    @Column(name = "MEMBERS", nullable = false)
    private int members = 1;

    @Column(name = "POSTS", nullable = false)
    private int posts = 0;

    @Column(name = "EVENTS", nullable = false)
    private int events = 0;

    @Column(name = "INS_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate = new Date();

    @Column(name = "DEL_FLG", nullable = false)
    private boolean delFlg = false;

}
