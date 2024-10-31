package com.qn_org.backend.controllers.student;

import com.qn_org.backend.models.StudentInfo;
import com.qn_org.backend.repositories.MajorRepository;
import com.qn_org.backend.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentInfoService {
    private final StudentRepository repository;
    private final MajorRepository majorRepository;

    public void save(StudentInfo info) {
        repository.save(info);
    }

    public StudentInfo update(UpdateStudentInfoRequest request) {
        StudentInfo studentInfo = repository.getReferenceById(request.getStudentKey());
        if(request.getMajorId() != null && !request.getMajorId().isBlank())
            studentInfo.setMajor(majorRepository.getReferenceById(request.getMajorId()));
        if(request.getFirstName() != null && !request.getFirstName().isBlank())
            studentInfo.setFirstName(request.getFirstName());
        if(request.getLastName() != null && !request.getLastName().isBlank())
            studentInfo.setLastName(request.getLastName());
        if(request.getFullName() != null && !request.getFullName().isBlank())
            studentInfo.setFullName(request.getFullName());
        if(request.getPhoneNumber() != null && !request.getPhoneNumber().isBlank())
            studentInfo.setPhoneNumber(request.getPhoneNumber());
        repository.save(studentInfo);
        return studentInfo;
    }

    public StudentInfo getById(String studentKey) {
        return repository.getReferenceById(studentKey);
    }
}
