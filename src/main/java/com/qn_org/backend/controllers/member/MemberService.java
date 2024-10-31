package com.qn_org.backend.controllers.member;

import com.qn_org.backend.models.Member;
import com.qn_org.backend.repositories.MemberRepository;
import com.qn_org.backend.repositories.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;
    private final OrganizationRepository orgRepository;
    public Member add(AddMemberToOrgRequest request) {
        Member member = Member.builder()
                .memberId("MEM_"+ UUID.randomUUID())
                .organization(orgRepository.getReferenceById(request.getOrgId()))
                .userId(request.getUserId())
                .roleId(request.getRoleId())
                .roleLevel(request.getRoleLevel())
                .insDate(new Date())
                .build();
        repository.save(member);
        return member;
    }

    public Member remove(MemberIdRequest request) {
        Member member = repository.getReferenceById(request.getMemberId());
        member.setDelFlg(true);
        repository.save(member);
        return member;
    }
}
