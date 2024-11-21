package com.qn_org.backend.repositories;

import com.qn_org.backend.controllers.user.StudentUserInfoResponse;
import com.qn_org.backend.controllers.user.UserInfoResponse;
import com.qn_org.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserId(String userId);

    @Query("""
                SELECT new com.qn_org.backend.controllers.user.UserInfoResponse(u.userId, u.emailAddress, u.displayName, u.userAvatar, u.userType)
                FROM User u
                WHERE u.userId IN :userIds
            """)
    List<UserInfoResponse> getUserInfos(@Param("userIds") List<String> userIds);

    @Query("""
                SELECT new com.qn_org.backend.controllers.user.StudentUserInfoResponse(u.userId, u.displayName, st.fullName, u.userAvatar, u.userBackground,st.major.majorId, mj.majorName, dep.departmentId, dep.depName, st.phoneNumber)
                FROM User u
                LEFT JOIN StudentInfo st ON u.userId = st.studentKey
                LEFT JOIN Major mj ON st.major.majorId = mj.majorId
                LEFT JOIN Department dep ON mj.department.departmentId = dep.departmentId
                WHERE u.userId = :userId
            """)
    StudentUserInfoResponse getStudentUserInfo(@Param("userId") String userId);
}
