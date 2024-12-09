package com.qn_org.backend.repositories;

import com.qn_org.backend.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department,String> {
    @Query(value = "SELECT * FROM Department WHERE DEL_FLG = 0 ORDER BY DEP_NAME LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Department> getAll(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT * FROM Department WHERE DEL_FLG = 1 ORDER BY DEP_NAME LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Department> getDeleted(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT COUNT(DEPARTMENT_ID) FROM Department WHERE DEL_FLG = 0", nativeQuery = true)
    Integer getAllTotal();

    @Query(value = "SELECT COUNT(DEPARTMENT_ID) FROM Department WHERE DEL_FLG = 1", nativeQuery = true)
    Integer getDeletedTotal();

    @Query(value = "SELECT * FROM Department WHERE DEL_FLG = 0 ORDER BY DEP_NAME", nativeQuery = true)
    List<Department> getAll();
}
