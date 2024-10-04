package com.qn_org.backend.repositories;

import com.qn_org.backend.models.ObservableNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObservableNotificationRepository extends JpaRepository<ObservableNotification,String> {
}
