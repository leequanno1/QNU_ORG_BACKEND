package com.qn_org.backend.controllers.member;

import com.qn_org.backend.models.Member;
import com.qn_org.backend.models.Organization;
import com.qn_org.backend.models.User;
import com.qn_org.backend.repositories.MemberRepository;
import com.qn_org.backend.repositories.OrganizationRepository;
import com.qn_org.backend.repositories.UserRepository;
import com.qn_org.backend.services.exceptions.NoAuthorityToDoActionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;
    private final UserRepository userRepository;
    private final OrganizationRepository orgRepository;
    public MemberDTO add(AddMemberToOrgRequest request) throws NoAuthorityToDoActionException {
        User user = userRepository.getReferenceById(request.getUserId());
        List<String> orgIdList = User.jsonStringToList(user.getOrgIds());
        Organization org = orgRepository.getReferenceById(request.getOrgId());
        if(orgIdList.contains(request.getOrgId())) {
           throw new NoAuthorityToDoActionException();
        }
        Member member = Member.builder()
                .memberId("MEM_"+ UUID.randomUUID())
                .organization(org)
                .userId(request.getUserId())
                .roleId(request.getRoleId())
                .roleLevel(request.getRoleLevel())
                .insDate(new Date())
                .build();
        repository.save(member);
        orgIdList.add(request.getOrgId());
        user.setOrgIds(User.listToJsonString(orgIdList));
        userRepository.save(user);
        org.setMembers(org.getMembers()+1);
        orgRepository.save(org);
        return new MemberDTO(member);
    }

    public MemberDTO remove(MemberIdRequest request) {
        Member member = repository.getReferenceById(request.getMemberId());
        member.setDelFlg(true);
        repository.save(member);
        return new MemberDTO(member);
    }

    public List<MemberInfo> getMemberInfo(MemberInfoRequest request) {
        if(request.getMemberIds().isEmpty()) {
            return new ArrayList<>();
        }
        return repository.getMemberInfo(request.getMemberIds());
    }

    public MemberInfo getMemberInfo(UserAndOrgIdRequest request) {
        return repository.getMemberInfo(request.getOrgId(), request.getUserId());
    }
}
