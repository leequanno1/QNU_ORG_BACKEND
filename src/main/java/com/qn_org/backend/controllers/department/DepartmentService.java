package com.qn_org.backend.controllers.department;

import com.qn_org.backend.common_requests.FromToIndexRequest;
import com.qn_org.backend.models.Department;
import com.qn_org.backend.repositories.DepartmentRepository;
import com.qn_org.backend.services.exceptions.IdNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository repository;
    public Department createDep(CreateDepRequest request) {
        Department dep = Department.builder()
                .departmentId("DEP_"+ UUID.randomUUID())
                .depName(request.getDepName())
                .insDate(new Date())
                .build();
        repository.save(dep);
        return dep;
    }

    public Department update(UpdateDepRequest request) throws IdNotExistException {
        try {
            Department dep = repository.getReferenceById(request.getDepartmentId());
            dep.setDepName(request.getDepName());
            repository.save(dep);
            return dep;
        } catch (Exception e) {
            throw new IdNotExistException();
        }
    }

    public Department delete(DepIdRequest request) throws IdNotExistException {
        try {
            Department dep = repository.getReferenceById(request.getDepartmentId());
            dep.setDelFlg(true);
            repository.save(dep);
            return dep;
        } catch (Exception e) {
            throw new IdNotExistException();
        }
    }

    public List<Department> getAll(FromToIndexRequest request) {
        if(!request.isValid()) {
            return new ArrayList<>();
        }
        return repository.getAll(request.getLimit(),request.getOffset());
    }

    public List<Department> getDeleted(FromToIndexRequest request) {
        if(!request.isValid()) {
            return new ArrayList<>();
        }
        return repository.getDeleted(request.getLimit(),request.getOffset());
    }

    public Department getById(String depId) {
        return repository.getReferenceById(depId);
    }

    public Integer getAllTotal() {
        return repository.getAllTotal();
    }

    public Integer getDeletedTotal() {
        return repository.getDeletedTotal();
    }
}
