package com.qn_org.backend.controllers.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberInfo {
    private String memberId;
    private String userId;
    private String emailAddress;
    private String displayName;
    private String userAvatar;
    private int userType;
}
