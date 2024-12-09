package com.qn_org.backend.controllers.organization;

import com.qn_org.backend.common_requests.FromToIndexRequest;
import com.qn_org.backend.config.JwtService;
import com.qn_org.backend.controllers.image.ImageService;
import com.qn_org.backend.controllers.member.MemberService;
import com.qn_org.backend.models.Organization;
import com.qn_org.backend.models.User;
import com.qn_org.backend.repositories.OrganizationRepository;
import com.qn_org.backend.repositories.UserRepository;
import com.qn_org.backend.services.JsonUtil;
import com.qn_org.backend.services.QnuService;
import com.qn_org.backend.services.exceptions.IdNotExistException;
import com.qn_org.backend.services.exceptions.NoAuthorityToDoActionException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationService implements QnuService<Organization> {

    private final OrganizationRepository repository;
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final MemberService memberService;
    private final JwtService jwtService;

    public Organization createOrganization(CreateOrganizationRequest request){
        Organization org = Organization.builder()
                .orgId("ORG_" + UUID.randomUUID())
                .orgName(request.getOrgName())
                .orgDescription("")
                .members(0)
                .insDate(new Date())
                .build();
        handleSaveRepository(org);
        if(request.getMemberIds() == null) {
            memberService.addMembers(org.getOrgId(), request.getAdminId(), new ArrayList<>());
        } else {
            memberService.addMembers(org.getOrgId(), request.getAdminId(), request.getMemberIds());
        }
        return org;
    }

    @Override
    public boolean handleSaveRepository(Organization entity) {
        if(entity != null) {
            repository.save(entity);
            return true;
        }
        return false;
    }

    private String generateImageName(){
        long time = new Date().getTime();
        return "IMG_" + time + UUID.randomUUID();
    }

    public List<Organization> getAll(FromToIndexRequest request) {
        if(!request.isValid()){
            return new ArrayList<>();
        }
        return repository.getAll(request.getLimit(),request.getOffset());
    }

    public Organization updateOrganization(UpdateOrganizationRequest request) throws IdNotExistException, IOException {
        try {
            Organization org = repository.getReferenceById(request.getOrgId());
            if(request.getOrgAvatar() != null && !request.getOrgAvatar().isEmpty()) {
                org.setOrgAvatar("/api/image/"+imageService.handleSaveImage(request.getOrgAvatar(),generateImageName()));
            }
            if(request.getOrgBackGround() != null && !request.getOrgBackGround().isEmpty()) {
                org.setOrgBackground("/api/image/"+imageService.handleSaveImage(request.getOrgBackGround(),generateImageName()));
            }
            if(request.getOrgName() != null && !request.getOrgName().isEmpty()) org.setOrgName(request.getOrgName());
            if(request.getOrgDescription() != null && !request.getOrgDescription().isEmpty()) org.setOrgDescription(request.getOrgDescription());
            repository.save(org);
            return org;
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IdNotExistException();
        }
    }

    public Organization deleteOrganization(String orgId, HttpServletRequest servletRequest) throws IdNotExistException, NoAuthorityToDoActionException {
        var userId = jwtService.extractUserId(servletRequest);
        var user = userRepository.getReferenceById(userId);
        if(!user.isSuperAdmin()){
            throw new NoAuthorityToDoActionException();
        }
        try {
            Organization org = repository.getReferenceById(orgId);
            org.setDelFlg(true);
            repository.save(org);
            return org;
        } catch (Exception e) {
            throw new IdNotExistException();
        }
    }

    @Transactional
    public Organization getById(String orgId) throws IdNotExistException {
        try {
            return repository.getReferenceById(orgId);
        } catch (Exception e) {
            throw new IdNotExistException();
        }
    }

    public List<Organization> getDeleted(FromToIndexRequest request) {
        if(!request.isValid()){
            return new ArrayList<>();
        }
        return repository.getDeleted(request.getLimit(),request.getOffset());
    }

    public Integer getAllTotal() {
        return repository.getAllTotal();
    }

    public Integer getDeletedTotal() {
        return repository.getDeletedTotal();
    }

    public List<Organization> getByUserId(String userId) {
        User user = userRepository.getReferenceById(userId);
        if(user.isSuperAdmin()) {
            return repository.findAllByDelFlg(false);
        }
        var ids = JsonUtil.jsonStringToList(user.getOrgIds());
        return ids.isEmpty() ? new ArrayList<>(): repository.getByUserID(ids);
    }
}
