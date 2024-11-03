package com.qn_org.backend.controllers.member;

import com.qn_org.backend.models.enums.MemberRole;
import com.qn_org.backend.services.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        List<String> roles = JsonUtil.jsonStringToList(roleId);
        int level = 0;
        for(String role: roles) {
            if(MemberRole.fromValue(Integer.parseInt(role)) == MemberRole.ADMIN && level < MemberRole.ADMIN.getValue()) {
                level = 2;
            }
            if(MemberRole.fromValue(Integer.parseInt(role)) == MemberRole.NORMAL_MEMBER && level < MemberRole.NORMAL_MEMBER.getValue())
                level = 1;
        }
        return level;
    }
}
