package com.qn_org.backend.controllers.event;

import com.qn_org.backend.common_requests.FromToIndexRequest;
import com.qn_org.backend.controllers.post.GetInOrgRequest;
import com.qn_org.backend.models.Event;
import com.qn_org.backend.responses.QnuResponseEntity;
import com.qn_org.backend.services.exceptions.ApprovalNoAuthorityException;
import com.qn_org.backend.services.exceptions.EditorNoAuthorityException;
import com.qn_org.backend.services.exceptions.NoAuthorityToDoActionException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
@CrossOrigin
public class EventController {
    private final EventService service;

    @PutMapping("/create")
    public QnuResponseEntity<EventDTO> create(CreateEventRequest request) throws IOException {
        return new QnuResponseEntity<>(service.create(request), HttpStatus.OK);
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

    @GetMapping("/get_all")
    public QnuResponseEntity<List<EventDTO>> getAll(@RequestBody FromToIndexRequest request, HttpServletRequest servletRequest) {
        return new QnuResponseEntity<>(service.getAll(request, servletRequest), HttpStatus.OK);
    }

    @GetMapping("/get_in_org")
    public QnuResponseEntity<List<EventDTO>> getInOrg(@RequestBody GetInOrgRequest request, HttpServletRequest servletRequest) {
        return new QnuResponseEntity<>(service.getInOrg(request, servletRequest), HttpStatus.OK);
    }

    @GetMapping("/get-not-approved-in-org")
    public QnuResponseEntity<List<EventDTO>> getNotApprovedInOrg(@RequestBody GetInOrgRequest request, HttpServletRequest servletRequest) {
        return new QnuResponseEntity<>(service.getNotApprovedInOrg(request, servletRequest), HttpStatus.OK);
    }

    @GetMapping("/get_by_id")
    public QnuResponseEntity<EventDTO> getById(@RequestParam String eventId, HttpServletRequest servletRequest) {
        return new QnuResponseEntity<>(service.getById(eventId, servletRequest), HttpStatus.OK);
    }
}
