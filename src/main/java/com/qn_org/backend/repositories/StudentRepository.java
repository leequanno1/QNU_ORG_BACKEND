package com.qn_org.backend.repositories;

import com.qn_org.backend.models.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentInfo,String> {
}
