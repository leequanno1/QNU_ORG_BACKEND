package com.qn_org.backend.models;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USER_NOTIFICATION")
public class UserNotification {

    @Id
    @Column(name = "USER_NOTI_ID", nullable = false)
    private String userNotiId;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "NOTIFICATION_ID", nullable = false)
    private ObservableNotification notification;

    @Column(name = "IS_READ", nullable = false)
    private boolean isRead = false;

    @Column(name = "INS_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;

    @Column(name = "DEL_DATE", nullable = false)
    private Date delDate;

    public String getUserNotiId() {
        return userNotiId;
    }

    public User getUser() {
        return user;
    }

    public ObservableNotification getNotification() {
        return notification;
    }

    public boolean isRead() {
        return isRead;
    }

    public Date getInsDate() {
        return insDate;
    }

    public Date getDelDate() {
        return delDate;
    }

    public void setUserNotiId(String userNotiId) {
        this.userNotiId = userNotiId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setNotification(ObservableNotification notification) {
        this.notification = notification;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }

    public void setDelDate(Date delDate) {
        this.delDate = delDate;
    }
}

