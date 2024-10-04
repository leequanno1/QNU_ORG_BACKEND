package com.qn_org.backend.repositories;

import com.qn_org.backend.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,String> {
}
