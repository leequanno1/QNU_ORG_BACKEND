package com.qn_org.backend.repositories;

import com.qn_org.backend.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, String> {
    @Query(value = "SELECT * FROM IMAGE WHERE PARENT_ID IN (:parentIds) AND DEL_FLG = 0", nativeQuery = true)
    List<Image> getImageByParentId(@Param("parentIds") List<String> parentIds);

    @Query(value = "UPDATE IMAGE SET DEL_FLG = 1 WHERE IMAGE_ID IN (:imageIds)", nativeQuery = true)
    void deleteAllById(@Param("imageIds") List<String> delImagesId);
}
