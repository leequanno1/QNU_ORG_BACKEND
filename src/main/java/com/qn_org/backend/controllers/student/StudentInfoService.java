package com.qn_org.backend.controllers.student;

import com.qn_org.backend.models.StudentInfo;
import com.qn_org.backend.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentInfoService {
    private final StudentRepository repository;

    public void update(StudentInfo info) {
        repository.save(info);
    }
}
