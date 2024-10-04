package com.qn_org.backend.repositories;

import com.qn_org.backend.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization,String> {
}
