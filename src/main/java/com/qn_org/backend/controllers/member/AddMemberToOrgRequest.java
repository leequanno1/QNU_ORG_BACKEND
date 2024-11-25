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
        return JsonUtil.getRoleLevel(roleId);
    }
}
