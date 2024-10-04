package com.qn_org.backend.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@Entity
@RequiredArgsConstructor
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

}

