package com.qn_org.backend.repositories;

import com.qn_org.backend.models.ValidationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidationCodeRepository extends JpaRepository<ValidationCode,String> {
    public ValidationCode findByValidationEmail(String emailAddress);
}
