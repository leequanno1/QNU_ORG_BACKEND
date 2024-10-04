package com.qn_org.backend.repositories;

import com.qn_org.backend.models.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNotificationRepository extends JpaRepository<UserNotification,String> {
}
