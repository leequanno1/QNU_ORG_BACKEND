package com.qn_org.backend.repositories;

import com.qn_org.backend.controllers.major.MajorExtend;
import com.qn_org.backend.models.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Stream;

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

    @Query("""
        SELECT new com.qn_org.backend.controllers.major.MajorExtend(m.majorId, m.department.departmentId, m.majorName, m.department.depName, m.delFlg, m.insDate) FROM Major m
        WHERE m.delFlg = false ORDER BY m.majorName
    """)
    List<MajorExtend> getAllForBoard();

    @Query(""" 
        SELECT new com.qn_org.backend.controllers.major.MajorExtend(m.majorId, m.department.departmentId, m.majorName, m.department.depName, m.delFlg, m.insDate) FROM Major m
        WHERE m.majorId in :majorIds AND m.delFlg = false ORDER BY m.majorName
    """)
    List<MajorExtend> getMajorExtendByIds(@Param("majorIds") List<String> majorIds);
}
