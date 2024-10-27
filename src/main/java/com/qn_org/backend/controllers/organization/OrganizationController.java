package com.qn_org.backend.controllers.organization;

import com.qn_org.backend.models.Organization;
import com.qn_org.backend.responses.QnuResponseEntity;
import com.qn_org.backend.services.exceptions.IdNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/organization")
@RequiredArgsConstructor
@CrossOrigin
public class OrganizationController {

    private final OrganizationService service;

    @PutMapping("/create")
    public QnuResponseEntity<Organization> createOrg(CreateOrganizationRequest request) throws IOException {
        return new QnuResponseEntity<>(service.createOrganization(request),HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public QnuResponseEntity<List<Organization>> getAll(@RequestBody FormToIndexRequest request) {
        return new QnuResponseEntity<>(service.getAll(request),HttpStatus.OK);
    }

    @GetMapping("/get-deleted")
    public QnuResponseEntity<List<Organization>> getDeleted(@RequestBody FormToIndexRequest request) {
        return new QnuResponseEntity<>(service.getDeleted(request),HttpStatus.OK);
    }

    @GetMapping("/get-by-id")
    public QnuResponseEntity<Organization> getById(@RequestParam String orgId) throws IdNotExistException {
        return new QnuResponseEntity<>(service.getById(orgId), HttpStatus.OK);
    }

    @PutMapping("/update")
    public QnuResponseEntity<Organization> updateOrg(UpdateOrganizationRequest request) throws IdNotExistException, IOException {
        return new QnuResponseEntity<>(service.updateOrganization(request), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public QnuResponseEntity<Organization> deleteOrg(@RequestBody IdRequest request) throws IdNotExistException {
        return new QnuResponseEntity<>(service.deleteOrganization(request.getOrgId()), HttpStatus.OK);
    }

    @GetMapping("/get-all-total")
    public QnuResponseEntity<Integer> getAllTotal() {
        return new QnuResponseEntity<>(service.getAllTotal(),HttpStatus.OK);
    }

    @GetMapping("/get-deleted-total")
    public QnuResponseEntity<Integer> getDeletedTotal() {
        return new QnuResponseEntity<>(service.getDeletedTotal(), HttpStatus.OK);
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