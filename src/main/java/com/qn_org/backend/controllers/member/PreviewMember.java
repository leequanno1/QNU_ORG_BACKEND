package com.qn_org.backend.controllers.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PreviewMember {
    private String userId;
    private String fullName;
    private String depName;
    private String orgId;
    private int userType;

    public PreviewMember(String userId, String fullNameSt, String depNameSt, String fullNameSf, String depNameSf,String orgId, int userType){
        this.userId = userId;
        this.fullName = fullNameSt == null || fullNameSt.isBlank() ? fullNameSf : fullNameSt;
        this.depName = depNameSt == null || depNameSt.isBlank() ? depNameSf : depNameSt;
        this.orgId = orgId;
        this.userType = userType;
    }
}
