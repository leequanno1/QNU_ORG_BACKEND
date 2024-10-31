package com.qn_org.backend.repositories;

import com.qn_org.backend.models.Major;
import org.hibernate.annotations.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MajorRepository extends JpaRepository<Major,String> {
    @Query(value = "SELECT * FROM Major WHERE DEL_FLG = 0 ORDER BY MAJOR_NAME LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Major> getAll(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT * FROM Major WHERE DEL_FLG = 1 ORDER BY MAJOR_NAME LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Major> getDeleted(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT COUNT(MAJOR_ID) FROM Major WHERE DEL_FLG = 0", nativeQuery = true)
    Integer getAllTotal();

    @Query(value = "SELECT COUNT(MAJOR_ID) FROM Major WHERE DEL_FLG = 1", nativeQuery = true)
    Integer getDeletedTotal();

    @Query(value = "SELECT * FROM Major WHERE DEL_FLG = 0 AND DEP_ID = :depId", nativeQuery = true)
    List<Major> getByDepId(@Param("depId") String depId);
}
