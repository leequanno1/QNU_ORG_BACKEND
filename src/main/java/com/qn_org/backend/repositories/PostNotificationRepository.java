package com.qn_org.backend.repositories;

import com.qn_org.backend.models.PostNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostNotificationRepository extends JpaRepository<PostNotification,String> {
}
