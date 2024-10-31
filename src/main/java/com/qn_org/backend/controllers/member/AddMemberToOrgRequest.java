package com.qn_org.backend.controllers.member;

import com.qn_org.backend.models.Organization;
import com.qn_org.backend.models.enums.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddMemberToOrgRequest {
    private String orgId;

    private String userId;

    private String roleId;

    public int getRoleLevel() {
        String[] roles = roleId.split("");
        int level = 0;
        for(String role: roles) {
            if(MemberRole.fromValue(Integer.parseInt(role)) == MemberRole.ADMIN && level < 2) {
                level = 2;
            }
            if(MemberRole.fromValue(Integer.parseInt(role)) == MemberRole.ADMIN && level < 1)
                level = 1;
        }
        return level;
    }
}
