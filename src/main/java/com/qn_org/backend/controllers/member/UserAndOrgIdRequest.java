package com.qn_org.backend.controllers.member;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAndOrgIdRequest {
    private String userId;
    private String orgId;
}
