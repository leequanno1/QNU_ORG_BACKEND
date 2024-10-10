package com.qn_org.backend.models;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@Entity
@RequiredArgsConstructor
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
    private Date insDate = new Date();

    @Column(name = "DEL_FLG", nullable = false)
    private boolean delFlg = false;

}

