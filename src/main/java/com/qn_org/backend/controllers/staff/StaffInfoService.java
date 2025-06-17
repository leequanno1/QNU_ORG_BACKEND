package com.qn_org.backend.controllers.staff;

import com.qn_org.backend.models.StaffInfo;
import com.qn_org.backend.repositories.DepartmentRepository;
import com.qn_org.backend.repositories.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StaffInfoService {
    private final StaffRepository repository;
    private final DepartmentRepository depRepository;

    public void save(StaffInfo info) {
        repository.save(info);
    }

    public StaffInfoDTO update(UpdateStaffInfoRequest request) {
        StaffInfo staffInfo = repository.getReferenceById(request.getStaffKey());
        if(request.getDepId() != null && !request.getDepId().isBlank())
            staffInfo.setDepartment(depRepository.getReferenceById(request.getDepId()));
        if(request.getFirstName() != null && !request.getFirstName().isBlank())
            staffInfo.setFirstName(request.getFirstName());
        if(request.getLastName() != null && !request.getLastName().isBlank())
            staffInfo.setLastName(request.getLastName());
        if(request.getFullName() != null && !request.getFullName().isBlank())
            staffInfo.setFullName(request.getFullName());
        if(request.getIsTeacher() != null)
            staffInfo.setTeacher(request.getIsTeacher());
        repository.save(staffInfo);
        return new StaffInfoDTO(staffInfo);
    }

    public StaffInfoDTO getById(String staffKey) {
        return new StaffInfoDTO(repository.getReferenceById(staffKey));
    }
}
