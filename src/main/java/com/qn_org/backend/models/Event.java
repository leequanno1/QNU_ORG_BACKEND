package com.qn_org.backend.models;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
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
    private Date insDate = new Date();

    @Column(name = "DEL_FLG", nullable = false)
    private boolean delFlg = false;

    @Column(name = "IS_APPROVED", nullable = false)
    private boolean isApproved = false;
}
