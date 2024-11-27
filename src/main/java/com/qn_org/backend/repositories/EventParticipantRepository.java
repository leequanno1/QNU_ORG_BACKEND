package com.qn_org.backend.repositories;

import com.qn_org.backend.models.EventParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventParticipantRepository extends JpaRepository<EventParticipant,String> {
    @Query("""
        SELECT evp.participantId FROM EventParticipant evp
        WHERE evp.delFlg = false AND evp.event.eventId = :eventId AND evp.user.userId = :userId
    """)
    String isExist(@Param("eventId") String eventId, @Param("userId") String userId);

    @Query("""
        SELECT evp.user.userId FROM EventParticipant evp
        WHERE evp.event.eventId = :eventId
    """)
    List<String> getUserIdsByEventId(@Param("eventId") String eventId);
}
