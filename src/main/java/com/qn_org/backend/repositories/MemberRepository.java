package com.qn_org.backend.repositories;

import com.qn_org.backend.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,String> {
    @Query(value = "SELECT MEMBER_ID FROM MEMBER WHERE USER_ID = :userId AND ORG_ID = :orgId",nativeQuery = true)
    String getMemberIdByUserIdAndOrgId(@Param("userId") String userId, @Param("orgId") String orgId);

    @Query(value = "SELECT MEMBER_ID FROM MEMBER WHERE USER_ID = :userId AND ORG_ID = :orgId AND ROLE_LEVEL = 2",nativeQuery = true)
    String getAdminIdByUserIdAndOrgId(@Param("userId") String userId, @Param("orgId") String orgId);
}
