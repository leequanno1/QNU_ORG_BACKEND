package com.qn_org.backend.repositories;

import com.qn_org.backend.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<String, Image> {
    @Query(value = "SELECT * FROM IMAGE WHERE PARENT_ID IN (:parentIds) AND DEL_FLG = 0", nativeQuery = true)
    List<Image> getImageByParentId(@Param(":parentIds") List<String> parentIds);
}
