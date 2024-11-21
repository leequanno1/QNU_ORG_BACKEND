package com.qn_org.backend.controllers.organization;

import com.qn_org.backend.common_requests.FromToIndexRequest;
import com.qn_org.backend.models.Organization;
import com.qn_org.backend.responses.QnuResponseEntity;
import com.qn_org.backend.services.exceptions.IdNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/organization")
@RequiredArgsConstructor
@CrossOrigin
public class OrganizationController {

    private final OrganizationService service;

    @PutMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public QnuResponseEntity<Organization> createOrg(@ModelAttribute CreateOrganizationRequest request) throws IOException {
        return new QnuResponseEntity<>(service.createOrganization(request),HttpStatus.OK);
    }

    @GetMapping("/get_all")
    public QnuResponseEntity<List<Organization>> getAll(@RequestBody FromToIndexRequest request) {
        return new QnuResponseEntity<>(service.getAll(request),HttpStatus.OK);
    }

    @GetMapping("/get_deleted")
    public QnuResponseEntity<List<Organization>> getDeleted(@RequestBody FromToIndexRequest request) {
        return new QnuResponseEntity<>(service.getDeleted(request),HttpStatus.OK);
    }

    @GetMapping("/get_by_id")
    public QnuResponseEntity<Organization> getById(@RequestParam String orgId) throws IdNotExistException {
        return new QnuResponseEntity<>(service.getById(orgId), HttpStatus.OK);
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public QnuResponseEntity<Organization> updateOrg(@ModelAttribute UpdateOrganizationRequest request) throws IdNotExistException, IOException {
        return new QnuResponseEntity<>(service.updateOrganization(request), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public QnuResponseEntity<Organization> deleteOrg(@RequestBody IdRequest request) throws IdNotExistException {
        return new QnuResponseEntity<>(service.deleteOrganization(request.getOrgId()), HttpStatus.OK);
    }

    @GetMapping("/get_all_total")
    public QnuResponseEntity<Integer> getAllTotal() {
        return new QnuResponseEntity<>(service.getAllTotal(),HttpStatus.OK);
    }

    @GetMapping("/get_deleted_total")
    public QnuResponseEntity<Integer> getDeletedTotal() {
        return new QnuResponseEntity<>(service.getDeletedTotal(), HttpStatus.OK);
    }

    @GetMapping("/get_by_user_id")
    public QnuResponseEntity<List<Organization>> getByUserId(@RequestParam String userId) {
        return new QnuResponseEntity<>(service.getByUserId(userId), HttpStatus.OK);
    }

    @ExceptionHandler(IdNotExistException.class)
    public QnuResponseEntity<String> idNotExistExceptionHandler() {
        return new QnuResponseEntity<>("Id is not existed!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public QnuResponseEntity<String> ioExceptionHandler() {
        return new QnuResponseEntity<>("There is an error while saving image(s)! Please retry later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
