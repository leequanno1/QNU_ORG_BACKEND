package com.qn_org.backend.controllers.event;

import com.qn_org.backend.common_requests.FromToIndexRequest;
import com.qn_org.backend.controllers.member.MemberDTO;
import com.qn_org.backend.controllers.member.MemberInfo;
import com.qn_org.backend.controllers.post.GetInOrgRequest;
import com.qn_org.backend.responses.QnuResponseEntity;
import com.qn_org.backend.services.exceptions.ApprovalNoAuthorityException;
import com.qn_org.backend.services.exceptions.EditorNoAuthorityException;
import com.qn_org.backend.services.exceptions.NoAuthorityToDoActionException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
@CrossOrigin
public class EventController {
    private final EventService service;

    @PostMapping(value ="/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public QnuResponseEntity<EventDTO> create(@ModelAttribute CreateEventRequest request, HttpServletRequest servletRequest) throws IOException, NoAuthorityToDoActionException {
        return new QnuResponseEntity<>(service.create(request, servletRequest), HttpStatus.OK);
    }

    @PostMapping("/approve")
    public QnuResponseEntity<EventDTO> approve(@RequestBody ApproveEventRequest request, HttpServletRequest servletRequest) throws ApprovalNoAuthorityException {
        return new QnuResponseEntity<>(service.approve(request, servletRequest), HttpStatus.OK);
    }

    @PostMapping("/edit")
    public QnuResponseEntity<EventDTO> edit(EditEventRequest request) throws EditorNoAuthorityException, IOException {
        return new QnuResponseEntity<>(service.edit(request), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public QnuResponseEntity<EventDTO> delete(@RequestBody DeleteEventRequest request) throws NoAuthorityToDoActionException {
        return new QnuResponseEntity<>(service.delete(request), HttpStatus.OK);
    }

    @PostMapping("/get_all")
    public QnuResponseEntity<List<EventDTO>> getAll(@RequestBody FromToIndexRequest request, HttpServletRequest servletRequest) {
        return new QnuResponseEntity<>(service.getAll(request, servletRequest), HttpStatus.OK);
    }

    @PostMapping("/get_in_org")
    public QnuResponseEntity<List<EventDTO>> getInOrg(@RequestBody GetInOrgRequest request, HttpServletRequest servletRequest) {
        return new QnuResponseEntity<>(service.getInOrg(request, servletRequest), HttpStatus.OK);
    }

    @PostMapping("/get_not_approved_in_org")
    public QnuResponseEntity<List<EventDTO>> getNotApprovedInOrg(@RequestBody GetInOrgRequest request, HttpServletRequest servletRequest) {
        return new QnuResponseEntity<>(service.getNotApprovedInOrg(request, servletRequest), HttpStatus.OK);
    }

    @GetMapping("/get_by_id")
    public QnuResponseEntity<EventDTO> getById(@RequestParam String eventId, HttpServletRequest servletRequest) {
        return new QnuResponseEntity<>(service.getById(eventId, servletRequest), HttpStatus.OK);
    }

    @PostMapping("/join_event")
    public QnuResponseEntity<EventDTO> joinEvent(@RequestBody JoinEventRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        return new QnuResponseEntity<>(service.joinEvent(request,servletRequest), HttpStatus.OK);
    }

    @PostMapping("/out_event")
    public QnuResponseEntity<EventDTO> outEvent(@RequestBody JoinEventRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        return new QnuResponseEntity<>(service.outEvent(request, servletRequest), HttpStatus.OK);
    }

    @PostMapping("/get_participant")
    public QnuResponseEntity<List<MemberInfo>> getParticipant(@RequestBody GetParticipantRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        return new QnuResponseEntity<>(service.getParticipant(request, servletRequest), HttpStatus.OK);
    }

    @ExceptionHandler(ApprovalNoAuthorityException.class)
    public QnuResponseEntity<String> handleApprovalNoAuthorityException() {
        return new QnuResponseEntity<>("User have no authority to do this action", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }

    @ExceptionHandler(NoAuthorityToDoActionException.class)
    public QnuResponseEntity<String> handleNoAuthorityToDoActionException() {
        return new QnuResponseEntity<>("User have no authority to do this action", HttpStatus.BAD_REQUEST);
    }
}
