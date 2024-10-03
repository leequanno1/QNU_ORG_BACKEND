package com.qn_org.backend.models;
import jakarta.persistence.*;
import java.util.Date;

@Entity
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

    public String getEventId() {
        return eventId;
    }

    public Date getBegin() {
        return begin;
    }

    public Date getEnd() {
        return end;
    }

    public Member getHoster() {
        return hoster;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public int getParticipants() {
        return participants;
    }

    public Date getInsDate() {
        return insDate;
    }

    public boolean isDelFlg() {
        return delFlg;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setHoster(Member hoster) {
        this.hoster = hoster;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }

    public void setDelFlg(boolean delFlg) {
        this.delFlg = delFlg;
    }
}
