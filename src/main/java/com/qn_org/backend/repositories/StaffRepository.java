package com.qn_org.backend.repositories;

import com.qn_org.backend.models.StaffInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<StaffInfo,String> {

}
