package com.qn_org.backend.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.qn_org.backend.controllers.event.EventDTO;
import com.qn_org.backend.repositories.OrganizationRepository;
import com.qn_org.backend.responses.QnuResponseEntity;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
@RequiredArgsConstructor
public class EventAspect {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OrganizationRepository organizationRepository;
    private final String EVENT_LOGGING_FILE_PATH = "D:\\GitHub\\ORG\\backend\\src\\public\\event_logging.csv";
    @Pointcut("execution(* com.qn_org.backend.controllers.event.EventController.joinEvent(..))")
    public void joinEventMethod() {}

    @AfterReturning(pointcut = "joinEventMethod()", returning = "result")
    public void logAfterJoinEventReturning(QnuResponseEntity<EventDTO> result) {
        try {
            var event = result.getBody() != null ? result.getBody().getData(): null;
            var org = organizationRepository.getReferenceById(event != null ? event.getOrgId() : "");
            ObjectNode dynamicObject = objectMapper.createObjectNode();
            if (event != null) {
                dynamicObject.put("eventId", event.getEventId());
                dynamicObject.put("begin", event.getBegin().toString());
                dynamicObject.put("end", event.getEnd().toString());
                dynamicObject.put("hosterId", event.getHosterId());
                dynamicObject.put("eventName", event.getEventName());
                dynamicObject.put("eventDescription", event.getEventDescription());
                dynamicObject.put("participants", event.getParticipants());
                dynamicObject.put("insDate", event.getInsDate().toString());
                dynamicObject.put("joinerId", event.getUserId());
                dynamicObject.put("orgId", event.getOrgId());
                dynamicObject.put("orgName", org.getOrgName());
            }
            String jsonString = dynamicObject.toString();
            CsvHelper.writeJsonToCsv(jsonString, EVENT_LOGGING_FILE_PATH);
            System.out.println("Response JSON: " + jsonString);
        } catch (Exception e) {
            System.err.println("Failed to log response JSON: " + e.getMessage());
        }
    }
}
