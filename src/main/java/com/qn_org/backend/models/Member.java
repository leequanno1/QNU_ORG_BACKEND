package com.qn_org.backend.models;
import jakarta.persistence.*;
import java.util.Date;

@Entity
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
    private Date insDate;

    @Column(name = "DEL_FLG", nullable = false)
    private boolean delFlg = false;

    // Getters and Setters

    public String getMemberId() {
        return memberId;
    }

    public Organization getOrganization() {
        return organization;
    }

    public String getUserId() {
        return userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public int getRoleLevel() {
        return roleLevel;
    }

    public Date getInsDate() {
        return insDate;
    }

    public boolean isDelFlg() {
        return delFlg;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setRoleLevel(int roleLevel) {
        this.roleLevel = roleLevel;
    }

    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }

    public void setDelFlg(boolean delFlg) {
        this.delFlg = delFlg;
    }
}
