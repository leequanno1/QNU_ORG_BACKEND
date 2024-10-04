package com.qn_org.backend.models;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@Entity
@RequiredArgsConstructor
@Table(name = "EVENT")
public class Event {

    @Id
    @Column(name = "EVENT_ID", nullable = false, unique = true)
    private String eventId;

    @Column(name = "BEGIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date begin;

    @Column(name = "END")
    @Temporal(TemporalType.TIMESTAMP)
    private Date end;

    @ManyToOne
    @JoinColumn(name = "HOSTER_ID", nullable = false)
    private Member hoster;

    @Column(name = "EVENT_NAME", nullable = false)
    private String eventName;

    @Column(name = "EVENT_DESCRIPTION")
    private String eventDescription;

    @Column(name = "PARTICIPANTS", nullable = false)
    private int participants = 1;

    @Column(name = "INS_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;

    @Column(name = "DEL_FLG", nullable = false)
    private boolean delFlg = false;

}
