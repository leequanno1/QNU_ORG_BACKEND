package com.qn_org.backend.repositories;

import com.qn_org.backend.controllers.event.EventDTO;
import com.qn_org.backend.models.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,String> {
//    @Query(value = "SELECT * FROM EVENT WHERE DEL_FLG = 0 AND IS_APPROVED = 1 AND ORG_ID IN (:orgIds) ORDER BY INS_DATE DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    @Query("""
        SELECT new com.qn_org.backend.controllers.event.EventDTO(e, e.hoster.userId, ep.user.userId) FROM Event e
        LEFT JOIN EventParticipant ep ON ep.user.userId = e.hoster.userId
        WHERE e.delFlg = false AND e.isApproved = true AND e.orgId IN :orgIds
        ORDER BY e.insDate DESC
    """)
    List<EventDTO> getAll(@Param("orgIds") List<String> orgIdList, Pageable pageable);

    @Query("""
        SELECT new com.qn_org.backend.controllers.event.EventDTO(e, e.hoster.userId, ep.user.userId) FROM Event e
        LEFT JOIN EventParticipant ep ON ep.user.userId = e.hoster.userId
        WHERE e.delFlg = false AND e.isApproved = true AND e.orgId = :orgId
        ORDER BY e.insDate DESC
    """)
    List<EventDTO> getInOrg(@Param("orgId") String orgId, Pageable pageable);

    @Query(value = "SELECT * FROM EVENT WHERE DEL_FLG = 0 AND IS_APPROVED = 0 AND ORG_ID = :orgId ORDER BY INS_DATE DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Event> getNotApprovedInOrg(@Param("orgId") String orgId, @Param("limit") int limit, @Param("offset") int offset);
}
