package com.qn_org.backend.models;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "OBSERVERBLE_NOTIFICATION")
public class ObservableNotification {

    @Id
    @Column(name = "NOTIFICATION_ID", nullable = false, unique = true)
    private String notificationId;

    @Column(name = "NOTI_AVATAR", nullable = false)
    private String notiAvatar;

    @Column(name = "NOTI_NAME", nullable = false)
    private String notiName;

    @Column(name = "NOTI_TYPE", nullable = false)
    private int notiType;

    @Column(name = "TARGET_URL", nullable = false)
    private String targetUrl;

    @Column(name = "INS_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;

    public String getNotificationId() {
        return notificationId;
    }

    public String getNotiAvatar() {
        return notiAvatar;
    }

    public String getNotiName() {
        return notiName;
    }

    public int getNotiType() {
        return notiType;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public Date getInsDate() {
        return insDate;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public void setNotiAvatar(String notiAvatar) {
        this.notiAvatar = notiAvatar;
    }

    public void setNotiName(String notiName) {
        this.notiName = notiName;
    }

    public void setNotiType(int notiType) {
        this.notiType = notiType;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }
}
