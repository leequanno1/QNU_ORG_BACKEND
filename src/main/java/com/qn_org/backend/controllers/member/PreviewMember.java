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
}
