package com.qn_org.backend.controllers.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffUserInfoResponse {
    private String userId;
    private String displayName;
    private String fullName;
    private String userAvatar;
    private String userBackground;
    private String departmentId;
    private String depName;
    private String phoneNumber;
}
