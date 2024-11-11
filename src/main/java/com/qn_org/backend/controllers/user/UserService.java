package com.qn_org.backend.controllers.user;

import com.qn_org.backend.repositories.MemberRepository;
import com.qn_org.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final MemberRepository memberRepository;

    public List<UserInfoResponse> getUserInfos(UserInfoRequest request) {
        if(request.getUserIds().isEmpty()) {
            return new ArrayList<>();
        }
        return repository.getUserInfos(request.getUserIds());
    }

    public List<UserInfoResponse> getUserInfosFromMemberId(UserInfosFromMemberIdRequest request) {
        if(request.getMemberId().isEmpty())
            return new ArrayList<>();
        List<String> userIdList = memberRepository.getUserIdByMemberId(request.getMemberId());
        return getUserInfos(new UserInfoRequest(userIdList));
    }
}
