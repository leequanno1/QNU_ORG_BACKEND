package com.qn_org.backend.repositories;

import com.qn_org.backend.models.EventParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventParticipantRepository extends JpaRepository<EventParticipant,String> {
}
