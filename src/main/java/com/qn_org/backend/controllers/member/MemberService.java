package com.qn_org.backend.controllers.member;

import com.qn_org.backend.config.JwtService;
import com.qn_org.backend.models.Member;
import com.qn_org.backend.models.enums.MemberRole;
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
    public List<MemberDTO> add(AddMemberToOrgRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        var userId = jwtService.extractUserId(servletRequest);
        var admin = repository.findByUserIdAndOrgId(userId,request.getOrgId());
        if(admin != null && MemberRole.isAdmin(admin.getRoleLevel())) {
            List<String> exitedUserIds = repository.getExitedUserId(request.getUserIds(), request.getOrgId());
            List<String> notExitedIds;
            if(exitedUserIds != null && !exitedUserIds.isEmpty()) {
                notExitedIds = getNotExitedUserId(request.getUserIds(), exitedUserIds);
            } else {
                notExitedIds = request.getUserIds();
            }
            List<Member> newMemberList = generateNewMember(notExitedIds, request.getOrgId());
            return MemberDTO.fromList(newMemberList);
        } else {
            throw new NoAuthorityToDoActionException();
        }
    }

    private List<Member> generateNewMember(List<String> notExitedIds, String orgId) {
        List<Member> list = new ArrayList<>();
        var org = orgRepository.getReferenceById(orgId);
        var users = userRepository.findUsersByUserIds(notExitedIds);
        if(notExitedIds.size() == users.size()) {
            for(int i = 0; i < users.size(); i++) {
                list.add(
                        Member.builder()
                                .memberId("MEM_"+ UUID.randomUUID())
                                .organization(org)
                                .userId(notExitedIds.get(i))
                                .roleId("['2']")
                                .roleLevel(1)
                                .insDate(new Date())
                                .build()
                );
                var orgIds = JsonUtil.jsonStringToList(users.get(i).getOrgIds());
                orgIds.add(orgId);
                users.get(i).setOrgIds(JsonUtil.listToJsonString(orgIds));
            }
        }
        repository.saveAll(list);
        userRepository.saveAll(users);
        return list;
    }

    private List<String> getNotExitedUserId(List<String> userIds, List<String> existedUserIds) {
        for(int i = 0; i < userIds.size(); i++) {
            if(existedUserIds.isEmpty()) {
                break;
            } else {
                for(int j = 0; j < existedUserIds.size(); j++) {
                    if(existedUserIds.get(j).equals(userIds.get(i))){
                        existedUserIds.remove(j);
                        userIds.remove(i);
                        break;
                    }
                }
            }
        }
        return userIds;
    }

    public MemberDTO remove(MemberIdRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        Member modifiedMember = repository.getReferenceById(request.getMemberId());
        String userId = jwtService.extractUserId(servletRequest);
        String memberId = repository.getMemberInfo(modifiedMember.getOrganization().getOrgId(), userId).getFirst().getMemberId();
        Member member = repository.getReferenceById(memberId);
        if(MemberRole.isAdmin(member.getRoleLevel()) &&
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
        if(MemberRole.isAdmin(member.getRoleLevel()) &&
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

    public List<PreviewMember> getPreviewMember(GetReviewMemberRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        var userId = jwtService.extractUserId(servletRequest);
        if(userId != null && !userId.isBlank()) {
            var member = repository.findByUserIdAndOrgId(userId, request.getOrgId());
            if(member != null && MemberRole.isAdmin(member.getRoleLevel())) {
                return repository.getPreviewMember(request.getUserIds());
            }
        }
        throw new NoAuthorityToDoActionException();
    }
}
