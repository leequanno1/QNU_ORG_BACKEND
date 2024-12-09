package com.qn_org.backend.repositories;

import com.qn_org.backend.controllers.member.PreviewMember;
import com.qn_org.backend.controllers.user.StaffUserInfoResponse;
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
        SELECT u FROM User u
        WHERE u.userId IN :userIds
    """)
    List<User> findUsersByUserIds(@Param("userIds") List<String> userIds);

    @Query("""
                SELECT new com.qn_org.backend.controllers.user.StudentUserInfoResponse(u.userId, u.displayName, st.fullName, u.userAvatar, u.userBackground,st.major.majorId, mj.majorName, dep.departmentId, dep.depName, st.phoneNumber)
                FROM User u
                LEFT JOIN StudentInfo st ON u.userId = st.studentKey
                LEFT JOIN Major mj ON st.major.majorId = mj.majorId
                LEFT JOIN Department dep ON mj.department.departmentId = dep.departmentId
                WHERE u.userId = :userId
            """)
    StudentUserInfoResponse getStudentUserInfo(@Param("userId") String userId);

    @Query("""
                SELECT new com.qn_org.backend.controllers.user.StaffUserInfoResponse(u.userId, u.displayName, st.fullName, u.userAvatar, u.userBackground, dep.departmentId, dep.depName, st.phoneNumber)
                FROM User u
                LEFT JOIN StaffInfo st ON u.userId = st.staffKey
                LEFT JOIN Department dep ON st.department.departmentId = dep.departmentId
                WHERE u.userId = :userId
            """)
    StaffUserInfoResponse getStaffUserInfo(@Param("userId") String userId);

    @Query("""
        SELECT new com.qn_org.backend.controllers.member.PreviewMember(
                                       u.userId,
                                       st.fullName,
                                       d.depName,
                                       sf.fullName,
                                       d2.depName,
                                       u.orgIds,
                                       u.userType
                                   )
                                   FROM User u
                                   LEFT JOIN StudentInfo st ON (u.userInfoKey LIKE 'STU%' AND u.userId = st.studentKey)
                                   LEFT JOIN Major mj ON st.major.majorId = mj.majorId
                                   LEFT JOIN Department d ON mj.department.departmentId = d.departmentId
                                   LEFT JOIN StaffInfo sf ON ((u.userInfoKey LIKE 'TEA%' OR u.userInfoKey LIKE 'STA%') AND u.userId = sf.staffKey)
                                   LEFT JOIN Department d2 ON sf.department.departmentId = d2.departmentId
                                   WHERE u.delFlg = false AND u.isSuperAdmin = false
                                   ORDER BY u.insDate DESC
    """)
    List<PreviewMember> getUsersForAdmin();
}
