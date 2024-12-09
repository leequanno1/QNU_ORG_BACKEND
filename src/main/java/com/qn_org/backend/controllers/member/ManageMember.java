package com.qn_org.backend.controllers.member;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ManageMember {
    private String memberId;

    private String orgId;

    private String userId;

    private String memberName;

    private String roleId;

    private int roleLevel;

    private Date insDate;

    private boolean delFlg;

    private int userType;

    private String depId;

    private String depName;

    public ManageMember(
            String memberId,
            String orgId,
            String userId,
            String memberName,
            String roleId,
            int roleLevel,
            Date insDate,
            boolean delFlg,
            String userInfoKey,
            String depId,
            String depName
    ){
        this.memberId = memberId;
        this.orgId = orgId;
        this.userId = userId;
        this.memberName = memberName;
        this.roleId = roleId;
        this.roleLevel = roleLevel;
        this.insDate = insDate;
        this.delFlg = delFlg;
        this.userType = handelGetUserType(userInfoKey);
        this.depId = depId;
        this.depName = depName;
    }

    public ManageMember(
            String memberId,
            String orgId,
            String userId,
            String memberName,
            String roleId,
            int roleLevel,
            Date insDate,
            boolean delFlg,
            String userInfoKey,
            String depIdSt,
            String depNameSt,
            String depIdSf,
            String depNameSf
    ){
        this.memberId = memberId;
        this.orgId = orgId;
        this.userId = userId;
        this.memberName = memberName;
        this.roleId = roleId;
        this.roleLevel = roleLevel;
        this.insDate = insDate;
        this.delFlg = delFlg;
        this.userType = handelGetUserType(userInfoKey);
        this.depId = depIdSt == null || depIdSt.isBlank() ? depIdSf : depIdSt;
        this.depName = depNameSt == null || depNameSt.isBlank() ? depNameSf : depNameSt;
    }

    private int handelGetUserType(String userInfoKey) {
        if(userInfoKey.startsWith("TEA")) {
            return 1;
        }
        if(userInfoKey.startsWith("STA")) {
            return 2;
        }
        if(userInfoKey.startsWith("STU")) {
            return 3;
        }
        return 0;
    }
}
