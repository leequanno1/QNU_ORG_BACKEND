package com.qn_org.backend.models;
import jakarta.persistence.*;
import lombok.*;

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
    private Date insDate;

}
