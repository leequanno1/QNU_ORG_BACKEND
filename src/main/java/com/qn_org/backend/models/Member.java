package com.qn_org.backend.models;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
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
@Table(name = "MEMBER")
public class Member {

    @Id
    @Column(name = "MEMBER_ID", nullable = false, unique = true)
    private String memberId;

    @ManyToOne
    @JoinColumn(name = "ORG_ID", nullable = false)
    private Organization organization;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "ROLE_ID", nullable = false)
    private String roleId;

    @Column(name = "ROLE_LEVEL", nullable = false)
    private int roleLevel;

    @Column(name = "INS_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate = new Date();

    @Column(name = "DEL_FLG", nullable = false)
    private boolean delFlg = false;

}
