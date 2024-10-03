package com.qn_org.backend.models;
import jakarta.persistence.*;
import java.util.Date;

@Entity
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

    // Getters and Setters

    public String getOrgId() {
        return orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getOrgDescription() {
        return orgDescription;
    }

    public String getOrgAvatar() {
        return orgAvatar;
    }

    public String getOrgBackground() {
        return orgBackground;
    }

    public int getMembers() {
        return members;
    }

    public int getPosts() {
        return posts;
    }

    public int getEvents() {
        return events;
    }

    public Date getInsDate() {
        return insDate;
    }

    public boolean isDelFlg() {
        return delFlg;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
    }

    public void setOrgAvatar(String orgAvatar) {
        this.orgAvatar = orgAvatar;
    }

    public void setOrgBackground(String orgBackground) {
        this.orgBackground = orgBackground;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }

    public void setEvents(int events) {
        this.events = events;
    }

    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }

    public void setDelFlg(boolean delFlg) {
        this.delFlg = delFlg;
    }
}
