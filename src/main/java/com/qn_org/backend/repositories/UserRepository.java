package com.qn_org.backend.repositories;

import com.qn_org.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByUserId(String userId);
}
