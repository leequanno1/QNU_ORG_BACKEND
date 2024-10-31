package com.qn_org.backend.controllers.major;

import com.qn_org.backend.common_requests.FromToIndexRequest;
import com.qn_org.backend.models.Major;
import com.qn_org.backend.repositories.DepartmentRepository;
import com.qn_org.backend.repositories.MajorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class MajorService {
    private final MajorRepository majorRepository;
    private final DepartmentRepository depRepository;

    public Major create(CreateMajorRequest request) {
        Major major = Major.builder()
                .department(depRepository.getReferenceById(request.getDepId()))
                .majorName(request.getMajorName())
                .insDate(new Date())
                .build();
        majorRepository.save(major);
        return major;
    }

    public Major update(UpdateMajorRequest request) {
        Major major = majorRepository.getReferenceById(request.getMajorId());
        major.setDepartment(depRepository.getReferenceById(request.getDepId()));
        major.setMajorName(request.getMajorName());
        majorRepository.save(major);
        return major;
    }

    public Major delete(MajorIdRequest request) {
        Major major = majorRepository.getReferenceById(request.getMajorId());
        major.setDelFlg(true);
        majorRepository.save(major);
        return major;
    }

    public List<Major> getAll(FromToIndexRequest request) {
        if(!request.isValid()) {
            return new ArrayList<>();
        }
        return majorRepository.getAll(request.getLimit(), request.getOffset());
    }


    public List<Major> getDeleted(FromToIndexRequest request) {
        if(!request.isValid()) {
            return new ArrayList<>();
        }
        return majorRepository.getDeleted(request.getLimit(), request.getOffset());
    }

    public Major getById(String majorId) {
        return majorRepository.getReferenceById(majorId);
    }

    public Integer getAllTotal() {
        return majorRepository.getAllTotal();
    }

    public Integer getDeletedTotal() {
        return majorRepository.getDeletedTotal();
    }

    public List<Major> getByDepId(String depId) {
        return majorRepository.getByDepId(depId);
    }
}
