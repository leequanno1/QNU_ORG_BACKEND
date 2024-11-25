package com.qn_org.backend.repositories;

import com.qn_org.backend.controllers.member.ManageMember;
import com.qn_org.backend.controllers.member.MemberInfo;
import com.qn_org.backend.controllers.member.PreviewMember;
import com.qn_org.backend.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,String> {
    @Query(value = "SELECT MEMBER_ID FROM MEMBER WHERE USER_ID = :userId AND ORG_ID = :orgId",nativeQuery = true)
    String getMemberIdByUserIdAndOrgId(@Param("userId") String userId, @Param("orgId") String orgId);

    @Query(value = "SELECT MEMBER_ID FROM MEMBER WHERE USER_ID = :userId AND ORG_ID = :orgId AND ROLE_LEVEL = 2",nativeQuery = true)
    String getAdminIdByUserIdAndOrgId(@Param("userId") String userId, @Param("orgId") String orgId);

    @Query(value = "SELECT USER_ID FROM MEMBER WHERE MEMBER_ID IN (:memberId)", nativeQuery = true)
    List<String> getUserIdByMemberId(@Param("memberId") List<String> memberId);

    @Query("""
        SELECT new com.qn_org.backend.controllers.member.MemberInfo(
            m.memberId, u.userId, u.emailAddress, u.displayName, u.userAvatar, u.userType,  m.roleLevel)
        FROM Member m
        JOIN User u
        ON m.userId = u.userId
        WHERE m.memberId IN :memberIds
    """)
    List<MemberInfo> getMemberInfo(@Param("memberIds") List<String> memberIds);

    @Query("""
        SELECT new com.qn_org.backend.controllers.member.MemberInfo(
            m.memberId, u.userId, u.emailAddress, u.displayName, u.userAvatar, u.userType,  m.roleLevel)
        FROM Member m
        JOIN User u
        ON m.userId = u.userId
        WHERE m.organization.orgId = :orgId
    """)
    List<MemberInfo> getMemberInfoByOrgId(@Param("orgId") String orgId);

    @Query("""
        SELECT new com.qn_org.backend.controllers.member.MemberInfo(
            m.memberId, u.userId, u.emailAddress, u.displayName, u.userAvatar, u.userType, m.roleLevel)
        FROM Member m
        JOIN User u
        ON m.userId = u.userId
        WHERE m.userId = :userId AND m.organization.orgId = :orgId
    """)
    List<MemberInfo> getMemberInfo(@Param("orgId") String orgId,@Param("userId") String userId);

    @Query("""
        SELECT new com.qn_org.backend.controllers.member.ManageMember(
                                       m.memberId,
                                       m.organization.orgId,
                                       m.userId,
                                       u.displayName,
                                       m.roleId,
                                       m.roleLevel,
                                       m.insDate,
                                       m.delFlg,
                                       u.userInfoKey,
                                       d.departmentId,
                                       d.depName
                                   )
                                   FROM Member m
                                   JOIN User u ON m.userId = u.userId
                                   LEFT JOIN StudentInfo st ON (u.userInfoKey LIKE 'STU%' AND m.userId = st.studentKey)
                                   LEFT JOIN Major mj ON st.major.majorId = mj.majorId
                                   LEFT JOIN Department d ON mj.department.departmentId = d.departmentId
                                   LEFT JOIN StaffInfo sf ON (u.userInfoKey LIKE 'TEA%' OR u.userInfoKey LIKE 'STA%' AND m.userId = sf.staffKey)
                                   LEFT JOIN Department d2 ON sf.department.departmentId = d2.departmentId
                                   WHERE m.organization.orgId = :orgId AND m.delFlg = false
                                   ORDER BY m.insDate DESC
    """)
    List<ManageMember> getManagedMember(@Param("orgId") String orgId);

    @Query("""
        SELECT m FROM Member m
        WHERE m.userId = :userId AND m.organization.orgId = :orgId
    """)
    Member findByUserIdAndOrgId(@Param("userId") String userId, @Param("orgId") String orgId);

    @Query("""
        SELECT new com.qn_org.backend.controllers.member.PreviewMember(
                                       u.userId,
                                       st.fullName,
                                       d.depName,
                                       u.orgIds,
                                       u.userType
                                   )
                                   FROM User u
                                   LEFT JOIN StudentInfo st ON (u.userInfoKey LIKE 'STU%' AND u.userId = st.studentKey)
                                   LEFT JOIN Major mj ON st.major.majorId = mj.majorId
                                   LEFT JOIN Department d ON mj.department.departmentId = d.departmentId
                                   LEFT JOIN StaffInfo sf ON (u.userInfoKey LIKE 'TEA%' OR u.userInfoKey LIKE 'STA%' AND u.userId = sf.staffKey)
                                   LEFT JOIN Department d2 ON sf.department.departmentId = d2.departmentId
                                   WHERE u.userId IN :userIds AND u.delFlg = false
                                   ORDER BY u.insDate DESC
    """)
    List<PreviewMember> getPreviewMember(@Param("userIds") List<String> userIds);

    @Query("""
        SELECT m.userId FROM Member m
        WHERE m.userId IN :userIds and m.organization.orgId = :orgId
    """)
    List<String> getExitedUserId(@Param("userIds") List<String> userIds, @Param("orgId") String orgId);
}
