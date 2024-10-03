package com.qn_org.backend.models;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "EVENT_NOTIFICATION")
public class EventNotification {

    @Id
    @Column(name = "EVENT_NOTI_ID", nullable = false, unique = true)
    private String eventNotiId;

    @Column(name = "ORG_AVATAR")
    private String orgAvatar;

    @Column(name = "ORG_NAME", nullable = false)
    private String orgName;

    @ManyToOne
    @JoinColumn(name = "EVENT_ID", nullable = false)
    private Event event;

    @Column(name = "INS_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;

    @Column(name = "DEL_FLG", nullable = false)
    private boolean delFlg = false;

    public String getEventNotiId() {
        return eventNotiId;
    }

    public String getOrgAvatar() {
        return orgAvatar;
    }

    public String getOrgName() {
        return orgName;
    }

    public Event getEvent() {
        return event;
    }

    public Date getInsDate() {
        return insDate;
    }

    public boolean isDelFlg() {
        return delFlg;
    }

    public void setEventNotiId(String eventNotiId) {
        this.eventNotiId = eventNotiId;
    }

    public void setOrgAvatar(String orgAvatar) {
        this.orgAvatar = orgAvatar;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }

    public void setDelFlg(boolean delFlg) {
        this.delFlg = delFlg;
    }
}

