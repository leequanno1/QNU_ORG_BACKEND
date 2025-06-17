package com.qn_org.backend.controllers.department;

import com.qn_org.backend.common_requests.FromToIndexRequest;
import com.qn_org.backend.models.Department;
import com.qn_org.backend.responses.QnuResponseEntity;
import com.qn_org.backend.services.exceptions.IdNotExistException;
import com.qn_org.backend.services.exceptions.NoAuthorityToDoActionException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
@CrossOrigin
public class DepartmentController {
    private final DepartmentService service;

    @PutMapping("/create")
    public QnuResponseEntity<Department> create(@RequestBody CreateDepRequest request) {
        return new QnuResponseEntity<>(service.createDep(request), HttpStatus.OK);
    }

    @PutMapping("/create_many")
    public QnuResponseEntity<List<Department>> createMany(@RequestBody CreateManyDepRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        return new QnuResponseEntity<>(service.createMany(request, servletRequest), HttpStatus.OK);
    }

    @PostMapping("/update")
    public QnuResponseEntity<DepartmentDTO> update(@RequestBody UpdateDepRequest request) throws IdNotExistException {
        return new QnuResponseEntity<>(service.update(request), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public QnuResponseEntity<DepartmentDTO> delete(@RequestBody DepIdRequest request) throws IdNotExistException {
        return new QnuResponseEntity<>(service.delete(request),HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public QnuResponseEntity<List<Department>> getAll(HttpServletRequest servletRequest) {
        return new QnuResponseEntity<>(service.getAll(servletRequest), HttpStatus.OK);
    }

    @PostMapping("/get-all")
    public QnuResponseEntity<List<Department>> getAll(@RequestBody FromToIndexRequest request){
        return new QnuResponseEntity<>(service.getAll(request),HttpStatus.OK);
    }

    @GetMapping("/get-deleted")
    public QnuResponseEntity<List<Department>> getDeleted(@RequestBody FromToIndexRequest request) {
        return new QnuResponseEntity<>(service.getDeleted(request), HttpStatus.OK);
    }

    @GetMapping("/get-by-id")
    public QnuResponseEntity<Department> getById(@RequestParam String depId) {
        return new QnuResponseEntity<>(service.getById(depId), HttpStatus.OK);
    }

    @GetMapping("/get-all-total")
    public QnuResponseEntity<Integer> getAllTotal() {
        return new QnuResponseEntity<>(service.getAllTotal(), HttpStatus.OK);
    }

    @GetMapping("/get-deleted-total")
    public QnuResponseEntity<Integer> getDeletedTotal() {
        return new QnuResponseEntity<>(service.getDeletedTotal(), HttpStatus.OK);
    }

    @ExceptionHandler(IdNotExistException.class)
    public QnuResponseEntity<String> idNotFoundHandler() {
        return new QnuResponseEntity<>("The Department id is not exist", HttpStatus.BAD_REQUEST);
    }
}
