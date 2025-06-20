package com.qn_org.backend.controllers.event;

import com.qn_org.backend.common_requests.FromToIndexRequest;
import com.qn_org.backend.config.JwtService;
import com.qn_org.backend.controllers.image.GetImagesRequest;
import com.qn_org.backend.controllers.image.ImageService;
import com.qn_org.backend.controllers.image.SaveImagesRequest;
import com.qn_org.backend.controllers.member.MemberInfo;
import com.qn_org.backend.controllers.post.GetInOrgRequest;
import com.qn_org.backend.models.*;
import com.qn_org.backend.models.enums.MemberRole;
import com.qn_org.backend.repositories.*;
import com.qn_org.backend.services.exceptions.ApprovalNoAuthorityException;
import com.qn_org.backend.services.exceptions.EditorNoAuthorityException;
import com.qn_org.backend.services.exceptions.NoAuthorityToDoActionException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository repository;
    private final MemberRepository memberRepository;
    private final ImageService imageService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final OrganizationRepository orgRepository;
    private final EventParticipantRepository eventPartiRepository;

    public EventDTO create(CreateEventRequest request, HttpServletRequest servletRequest) throws IOException, NoAuthorityToDoActionException {
        String userId = jwtService.extractUserId(servletRequest);
        Member member = memberRepository.getReferenceById(request.getHosterId());
        if(!member.getUserId().equals(userId)) {
            throw new NoAuthorityToDoActionException();
        }
        Organization org = member.getOrganization();
        if(MemberRole.isAdmin(member.getRoleLevel())){
            org.setEvents(org.getEvents()+1);
        }
        Event event = Event.builder()
                .eventId("EVN_" + UUID.randomUUID())
                .begin(request.getBegin())
                .end(request.getEnd())
                .hoster(member)
                .eventName(request.getEventName())
                .eventDescription(request.getEventDescription())
                .insDate(new Date())
                .isApproved(MemberRole.isAdmin(member.getRoleLevel()))
                .orgId(org.getOrgId())
                .build();
        repository.save(event);
        orgRepository.save(org);
        var images = imageService.saveImages(new SaveImagesRequest(event.getEventId(),request.getImages() == null ? new ArrayList<>() : request.getImages()));
        return new EventDTO(event, images);
    }

    public EventDTO approve(ApproveEventRequest request, HttpServletRequest servletRequest) throws ApprovalNoAuthorityException {
        Event event = repository.getReferenceById(request.getEventId());
        String userId = jwtService.extractUserId(servletRequest);
        Member member = memberRepository.getReferenceById(request.getApprovalId());
        if(!(
                MemberRole.isAdmin(member.getRoleLevel()) &&
                        member.getOrganization().getOrgId().equals(event.getOrgId()) &&
                        member.getUserId().equals(userId)
        ))
            throw new ApprovalNoAuthorityException();
        event.setApproved(true);
        var org = orgRepository.getReferenceById(member.getOrganization().getOrgId());
        org.setEvents(org.getEvents()+1);
        repository.save(event);
        orgRepository.save(org);
        // TODO: Handle add notification here.
        return new EventDTO(event);
    }

    public EventDTO edit(EditEventRequest request) throws EditorNoAuthorityException, IOException {
        Event event = repository.getReferenceById(request.getEventId());
        if(!event.getHoster().getMemberId().equals(request.getHosterId())) {
            throw new EditorNoAuthorityException();
        }
        event.setEventName(request.getEventName());
        event.setEventDescription(request.getEventDescription());
        repository.save(event);
        if(request.getDelImagesId() != null && !request.getDelImagesId().isEmpty()) {
            imageService.deleteImage(request.getDelImagesId());
        }
        if(request.getNewImage() != null && !request.getNewImage().isEmpty()) {
            imageService.saveImages(new SaveImagesRequest(event.getEventId(), request.getNewImage()));
        }
        List<String> parentId = new ArrayList<>();
        parentId.add(event.getEventId());
        var images = imageService.getImage(GetImagesRequest.builder().parentIds(parentId).build());
        return new EventDTO(event, images);
    }

    public EventDTO delete(DeleteEventRequest request) throws NoAuthorityToDoActionException {
        Event event = repository.getReferenceById(request.getEventId());
        boolean isDeleted = false;
        Member member = memberRepository.getReferenceById(request.getHosterId());
        User user = userRepository.getReferenceById(member.getUserId());
        if(user.isSuperAdmin())
            isDeleted = true;
        if(member.getMemberId().equals(request.getEventId()) || MemberRole.isAdmin(member.getRoleLevel()))
            isDeleted = true;
        if(isDeleted) {
            event.setDelFlg(true);
            repository.save(event);
            var org = orgRepository.getReferenceById(member.getOrganization().getOrgId());
            org.setEvents(org.getEvents()-1);
            orgRepository.save(org);
            return new EventDTO(event);
        } else {
            throw new NoAuthorityToDoActionException();
        }
    }

    public List<EventDTO> getAll(FromToIndexRequest request, HttpServletRequest servletRequest) {
        String userId = jwtService.extractUserId(servletRequest);
        String orgIds = userRepository.getReferenceById(userId).getOrgIds();
        List<String> orgIdList = User.jsonStringToList(orgIds);
        if(!request.isValid())
            return new ArrayList<>();
        return repository.getAll(orgIdList,userId ,PageRequest.of(request.getPage(), request.getSize()));
    }

    public List<EventDTO> getInOrg(GetInOrgRequest request, HttpServletRequest servletRequest) {
        String userId = jwtService.extractUserId(servletRequest);
        String orgIds = userRepository.getReferenceById(userId).getOrgIds();
        List<String> orgIdList = User.jsonStringToList(orgIds);
        if(orgIdList.contains(request.getOrgId())) {
            return repository.getInOrg(request.getOrgId(), userId,PageRequest.of(request.getOffset().getPage(), request.getOffset().getSize()));
        }
        return new ArrayList<>();
    }

    public List<EventDTO> getNotApprovedInOrg(GetInOrgRequest request, HttpServletRequest servletRequest) {
        String userId = jwtService.extractUserId(servletRequest);
        String orgIds = userRepository.getReferenceById(userId).getOrgIds();
        String adminId = memberRepository.getAdminIdByUserIdAndOrgId(userId, request.getOrgId());
        List<String> orgIdList = User.jsonStringToList(orgIds);
        if(orgIdList.contains(request.getOrgId()) && adminId != null && !adminId.isBlank()) {
            return EventDTO.fromList(repository.getNotApprovedInOrg(request.getOrgId(), request.getOffset().getLimit(), request.getOffset().getOffset()));
        }
        return new ArrayList<>();
    }

    public EventDTO getById(String eventId, HttpServletRequest servletRequest) {
        Event event = repository.getReferenceById(eventId);
        User user = userRepository.getReferenceById(jwtService.extractUserId(servletRequest));
        List<String> orgIdList = User.jsonStringToList(user.getOrgIds());
        if(orgIdList.contains(user.getOrgIds())) {
            return new EventDTO(event);
        }
        return null;
    }

    public EventDTO joinEvent(JoinEventRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        var userId = jwtService.extractUserId(servletRequest);
        var user = userRepository.getReferenceById(userId);
        var event = repository.getReferenceById(request.getEventId());
        if(event.getBegin().getTime() < new Date().getTime()) {
            throw new NoAuthorityToDoActionException();
        }
        String eventPartiId = eventPartiRepository.isExist(event.getEventId(), user.getUserId());
        EventParticipant eventParti;
        if(eventPartiId != null && !eventPartiId.isBlank()) {
            eventParti = eventPartiRepository.getReferenceById(eventPartiId);
            if(!eventParti.isDelFlg()) {
                return new EventDTO(event, user.getUserId(), true);
            }
            eventParti.setDelFlg(false);
        } else {
            eventParti = EventParticipant.builder()
                    .participantId("EVP_"+UUID.randomUUID())
                    .event(event)
                    .user(user)
                    .insDate(new Date())
                    .delFlg(false)
                    .build();
        }
        event.setParticipants(event.getParticipants()+1);
        repository.save(event);
        eventPartiRepository.save(eventParti);
        return new EventDTO(event, user.getUserId(), true);
    }

    public EventDTO outEvent(JoinEventRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        var userId = jwtService.extractUserId(servletRequest);
        var user = userRepository.getReferenceById(userId);
        var event = repository.getReferenceById(request.getEventId());
        if(event.getBegin().getTime() < new Date().getTime()) {
            throw new NoAuthorityToDoActionException();
        }
        String eventPartiId = eventPartiRepository.isExist(event.getEventId(), user.getUserId());
        if(eventPartiId != null && !eventPartiId.isBlank()) {
            EventParticipant eventParti = eventPartiRepository.getReferenceById(eventPartiId);
            if(eventParti.isDelFlg()) {
                return new EventDTO(event, user.getUserId(), false);
            }
//            eventParti.setDelFlg(true);
            eventPartiRepository.delete(eventParti);
            event.setParticipants(event.getParticipants()-1);
            repository.save(event);
        }
        return new EventDTO(event, user.getUserId(), false);
    }

    public List<MemberInfo> getParticipant(GetParticipantRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        var userId = jwtService.extractUserId(servletRequest);
        var event = repository.getReferenceById(request.getEventId());
        if(event.getEventId() != null && !event.getEventId().isBlank()) {
            var member = memberRepository.getMemberInfo(event.getOrgId(), userId);
            if(member != null && !member.isEmpty()) {
                var memberInfo = member.getFirst();
                if(!(memberInfo.getMemberId().equals(event.getHoster().getMemberId()) || MemberRole.isAdmin(memberInfo.getRoleLevel()))){
                    throw new NoAuthorityToDoActionException();
                }
            }
            var orgId = event.getOrgId();
            List<String> userIds = eventPartiRepository.getUserIdsByEventId(event.getEventId());
            return memberRepository.getMemberInfos(orgId, userIds);
        }
        return new ArrayList<>();
    }
}
