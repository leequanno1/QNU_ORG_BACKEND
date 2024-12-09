package com.qn_org.backend.controllers.member;

import com.qn_org.backend.config.JwtService;
import com.qn_org.backend.models.Member;
import com.qn_org.backend.models.User;
import com.qn_org.backend.models.enums.MemberRole;
import com.qn_org.backend.repositories.MemberRepository;
import com.qn_org.backend.repositories.OrganizationRepository;
import com.qn_org.backend.repositories.UserRepository;
import com.qn_org.backend.services.JsonUtil;
import com.qn_org.backend.services.exceptions.NoAuthorityToDoActionException;
import io.swagger.v3.core.util.Json;
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
        var admin = Member.builder().build();
        var user = userRepository.getReferenceById(userId);
        if(!user.isSuperAdmin()) {
            admin = repository.findByUserIdAndOrgId(userId,request.getOrgId());
        }
        if(user.isSuperAdmin() || (admin != null && MemberRole.isAdmin(admin.getRoleLevel()))) {
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

    public List<Member> addMembers(String orgId, String adminId, List<String> memberIds) {
        List<Member> members = generateNewMember(memberIds, orgId);
        members.add(generateNewAdmin(adminId, orgId));
        return members;
    }

    private Member generateNewAdmin(String userId, String orgId) {
        var org = orgRepository.getReferenceById(orgId);
        var user = userRepository.getReferenceById(userId);
        var mem = Member.builder()
                .memberId("MEM_"+ UUID.randomUUID())
                .organization(org)
                .userId(userId)
                .roleId("['1']")
                .roleLevel(2)
                .insDate(new Date())
                .build();
        var orgIds = JsonUtil.jsonStringToList(user.getOrgIds());
        orgIds.add(orgId);
        user.setOrgIds(JsonUtil.listToJsonString(orgIds));
        repository.save(mem);
        userRepository.save(user);
        return mem;
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
        User user = userRepository.getReferenceById(userId);
        Member member = Member.builder().build();
        if(!user.isSuperAdmin()) {
            String memberId = repository.getMemberInfo(modifiedMember.getOrganization().getOrgId(), userId).getFirst().getMemberId();
            member = repository.getReferenceById(memberId);
        }
        if(user.isSuperAdmin() || (MemberRole.isAdmin(member.getRoleLevel()) &&
            member.getOrganization().getOrgId().equals(modifiedMember.getOrganization().getOrgId()) &&
            !member.getMemberId().equals(request.getMemberId()))
        )
        {
            modifiedMember.setDelFlg(true);
            repository.save(modifiedMember);
            User addedUser = userRepository.getReferenceById(modifiedMember.getUserId());
            var orgIds = JsonUtil.jsonStringToList(addedUser.getOrgIds());
            orgIds.remove(modifiedMember.getOrganization().getOrgId());
            addedUser.setOrgIds(JsonUtil.listToJsonString(orgIds));
            userRepository.save(addedUser);
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
        User user = userRepository.getReferenceById(userId);
        Member member = Member.builder().build();
        if(!user.isSuperAdmin()){
            String memberId = repository.getMemberInfo(request.getOrgId(),userId).getFirst().getMemberId();
            member = repository.getReferenceById(memberId);
        }
        if(user.isSuperAdmin() || (MemberRole.isAdmin(member.getRoleLevel()) &&
            member.getOrganization().getOrgId().equals(request.getOrgId()) &&
            !member.getMemberId().equals(request.getMemberId())))
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
            User user = userRepository.getReferenceById(userId);
            var member = Member.builder().build();
            if(user.isSuperAdmin()) {
                member = repository.findByUserIdAndOrgId(userId, request.getOrgId());
            }
            if(user.isSuperAdmin() || (member != null && MemberRole.isAdmin(member.getRoleLevel()))) {
                return repository.getPreviewMember(request.getUserIds());
            }
        }
        throw new NoAuthorityToDoActionException();
    }
}
