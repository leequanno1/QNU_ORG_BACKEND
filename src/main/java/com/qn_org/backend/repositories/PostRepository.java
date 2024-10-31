package com.qn_org.backend.repositories;

import com.qn_org.backend.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,String> {
    @Query(value = "SELECT * FROM Post WHERE DEL_FLG = 0 AND IS_APPROVED = 1 ORDER BY INS_DATE LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Post> getAll(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT * FROM Post WHERE DEL_FLG = 0 AND ORG_ID = :orgId AND IS_APPROVED = 1 ORDER BY INS_DATE LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Post> getInOrg(@Param("orgId") String orgId, @Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT * FROM Post WHERE DEL_FLG = 0 AND ORG_ID = :orgId AND IS_APPROVED = 0 ORDER BY INS_DATE LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Post> getNotApprovedInOrg(@Param("orgId") String orgId, @Param("limit") int limit, @Param("offset") int offset);
}
