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
@Table(name = "EVENT_PARTICIPANT")
public class EventParticipant {

    @Id
    @Column(name = "PARTICIPANT_ID", nullable = false, unique = true)
    private String participantId;

    @ManyToOne
    @JoinColumn(name = "EVENT_ID", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "INS_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate = new Date();

    @Column(name = "DEL_FLG", nullable = false)
    private boolean delFlg = false;

}
