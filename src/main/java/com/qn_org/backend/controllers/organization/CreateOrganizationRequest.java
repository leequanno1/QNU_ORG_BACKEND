package com.qn_org.backend.controllers.organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrganizationRequest {
    private String orgName;
    private String adminId;
    private List<String> memberIds;
}
