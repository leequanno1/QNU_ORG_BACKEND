package com.qn_org.backend.controllers.member;

import com.qn_org.backend.config.JwtService;
import com.qn_org.backend.models.Member;
import com.qn_org.backend.models.Organization;
import com.qn_org.backend.models.User;
import com.qn_org.backend.repositories.MemberRepository;
import com.qn_org.backend.repositories.OrganizationRepository;
import com.qn_org.backend.repositories.UserRepository;
import com.qn_org.backend.services.JsonUtil;
import com.qn_org.backend.services.exceptions.NoAuthorityToDoActionException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;
    private final UserRepository userRepository;
    private final OrganizationRepository orgRepository;
    private final JwtService jwtService;
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

    public MemberDTO remove(MemberIdRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        Member modifiedMember = repository.getReferenceById(request.getMemberId());
        String userId = jwtService.extractUserId(servletRequest);
        String memberId = repository.getMemberInfo(modifiedMember.getOrganization().getOrgId(), userId).getFirst().getMemberId();
        Member member = repository.getReferenceById(memberId);
        if(
            member.getRoleLevel() == 2 &&
            member.getOrganization().getOrgId().equals(modifiedMember.getOrganization().getOrgId()) &&
            !member.getMemberId().equals(request.getMemberId())
        )
        {
            modifiedMember.setDelFlg(true);
            repository.save(modifiedMember);
            return new MemberDTO(modifiedMember);
        } else {
            throw new NoAuthorityToDoActionException();
        }
    }

    public List<MemberInfo> getMemberInfo(MemberInfoRequest request) {
        if(request.getMemberIds().isEmpty()) {
            return new ArrayList<>();
        }
        return repository.getMemberInfo(request.getMemberIds());
    }

    public MemberInfo getMemberInfo(UserAndOrgIdRequest request) {
        var data = repository.getMemberInfo(request.getOrgId(), request.getUserId());
        if(!data.isEmpty()) {
            return data.getFirst();
        }
        return null;
    }

    public List<ManageMember> getManagedMembers(String orgId) {
        return repository.getManagedMember(orgId);
    }

    public ManageMember changeMemberRole(ManageMember request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        String userId = jwtService.extractUserId(servletRequest);
        String memberId = repository.getMemberInfo(request.getOrgId(),userId).getFirst().getMemberId();
        Member member = repository.getReferenceById(memberId);
        if(
            member.getRoleLevel() == 2 &&
            member.getOrganization().getOrgId().equals(request.getOrgId()) &&
            !member.getMemberId().equals(request.getMemberId()))
        {
            Member modifiedMember = repository.getReferenceById(request.getMemberId());
            modifiedMember.setRoleId(request.getRoleId());
            modifiedMember.setRoleLevel(JsonUtil.getRoleLevel(request.getRoleId()));
            repository.save(modifiedMember);
            return request;
        } else {
          throw new NoAuthorityToDoActionException();
        }
    }
}
