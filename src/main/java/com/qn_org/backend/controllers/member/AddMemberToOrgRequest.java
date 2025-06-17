package com.qn_org.backend.controllers.member;

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

    private List<String> userIds;
}
