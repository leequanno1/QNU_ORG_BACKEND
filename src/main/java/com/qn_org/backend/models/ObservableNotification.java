package com.qn_org.backend.models;
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
    private Date insDate = new Date();

}
