package com.qn_org.backend.controllers.organization;

import com.qn_org.backend.common_requests.FromToIndexRequest;
import com.qn_org.backend.controllers.image.ImageService;
import com.qn_org.backend.models.Organization;
import com.qn_org.backend.repositories.OrganizationRepository;
import com.qn_org.backend.services.QnuService;
import com.qn_org.backend.services.exceptions.IdNotExistException;
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

    public Organization createOrganization(CreateOrganizationRequest request) throws IOException {
        String avtName = "";
        String backgroundName = "";
        avtName = imageService.handleSaveImage(request.getOrgAvatar(), generateImageName());
        backgroundName = imageService.handleSaveImage(request.getOrgBackGround(), generateImageName());
        Organization org = Organization.builder()
                .orgId("ORG_" + UUID.randomUUID())
                .orgAvatar(avtName)
                .orgBackground(backgroundName)
                .orgName(request.getOrgName())
                .orgDescription(request.getOrgDescription())
                .members(0)
                .insDate(new Date())
                .build();
        handleSaveRepository(org);
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
                org.setOrgAvatar(imageService.handleSaveImage(request.getOrgAvatar(),generateImageName()));
            }
            if(request.getOrgBackGround() != null && !request.getOrgBackGround().isEmpty()) {
                org.setOrgBackground(imageService.handleSaveImage(request.getOrgBackGround(),generateImageName()));
            }
            if(request.getOrgName() != null && !request.getOrgName().isEmpty()) org.setOrgName(request.getOrgName());
            if(request.getOrgDescription() != null && !request.getOrgDescription().isEmpty()) org.setOrgDescription(request.getOrgDescription());
            repository.save(org);
            return org;
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new IdNotExistException();
        }
    }

    public Organization deleteOrganization(String orgId) throws IdNotExistException {
        try {
            Organization org = repository.getReferenceById(orgId);
            org.setDelFlg(true);
            repository.save(org);
            return org;
        } catch (Exception e) {
            throw new IdNotExistException();
        }
    }

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
}
