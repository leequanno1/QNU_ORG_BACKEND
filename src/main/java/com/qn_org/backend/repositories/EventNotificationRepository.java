package com.qn_org.backend.repositories;

import com.qn_org.backend.models.EventNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventNotificationRepository extends JpaRepository<EventNotification,String> {
}
