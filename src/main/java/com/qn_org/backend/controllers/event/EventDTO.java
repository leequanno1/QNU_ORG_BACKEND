package com.qn_org.backend.controllers.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qn_org.backend.models.Event;
import com.qn_org.backend.models.Image;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDTO {
    private String eventId;

    private Date begin;

    private Date end;

    private String hosterId;

    private String eventName;

    private String eventDescription;

    private int participants;

    private Date insDate;

    private boolean delFlg;

    private boolean isApproved;

    private String orgId;

    private List<Image> images;

    public EventDTO(Event event) {
        this.eventId = event.getEventId();
        this.begin = event.getBegin();
        this.end = event.getEnd();
        this.hosterId = event.getHoster().getMemberId();
        this.eventName = event.getEventName();
        this.eventDescription = event.getEventDescription();
        this.participants = event.getParticipants();
        this.insDate = event.getInsDate();
        this.delFlg = event.isDelFlg();
        this.isApproved = event.isApproved();
        this.orgId = event.getOrgId();
    }

    public EventDTO(Event event, List<Image> images) {
        this.eventId = event.getEventId();
        this.begin = event.getBegin();
        this.end = event.getEnd();
        this.hosterId = event.getHoster().getMemberId();
        this.eventName = event.getEventName();
        this.eventDescription = event.getEventDescription();
        this.participants = event.getParticipants();
        this.insDate = event.getInsDate();
        this.delFlg = event.isDelFlg();
        this.isApproved = event.isApproved();
        this.orgId = event.getOrgId();
        this.images = images;
    }

    public static List<EventDTO> fromList(List<Event> events) {
        List<EventDTO> dto = new ArrayList<>();
        for(Event event: events) {
            dto.add(new EventDTO(event));
        }
        return dto;
    }
}
