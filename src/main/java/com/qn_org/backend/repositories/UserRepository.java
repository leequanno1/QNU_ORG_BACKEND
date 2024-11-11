package com.qn_org.backend.repositories;

import com.qn_org.backend.controllers.user.UserInfoResponse;
import com.qn_org.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByUserId(String userId);

    @Query(value = "SELECT USER_ID, EMAIL_ADDRESS, DISPLAY_NAME, USER_AVATAR, USER_TYPE FROM USER WHERE USER_ID IN (:userIds)", nativeQuery = true)
    List<UserInfoResponse> getUserInfos(@Param("userIds") List<String> userIds);
}
