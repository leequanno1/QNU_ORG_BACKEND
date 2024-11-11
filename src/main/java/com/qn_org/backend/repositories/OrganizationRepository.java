package com.qn_org.backend.repositories;

import com.qn_org.backend.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization,String> {
    @Query(value = "SELECT * FROM Organization WHERE DEL_FLG = 0 ORDER BY ORG_NAME LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Organization> getAll(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT * FROM Organization WHERE DEL_FLG = 1 ORDER BY ORG_NAME LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Organization> getDeleted(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT COUNT(ORG_ID) AS total FROM Organization WHERE DEL_FLG = 0", nativeQuery = true)
    Integer getAllTotal();

    @Query(value = "SELECT COUNT(ORG_ID) AS total FROM Organization WHERE DEL_FLG = 1", nativeQuery = true)
    Integer getDeletedTotal();

    @Query(value = "SELECT * FROM Organization WHERE ORG_ID IN (:orgIds) AND DEL_FLG = 0", nativeQuery = true)
    List<Organization> getByUserID(@Param("orgIds") List<String> orgIds);
}
