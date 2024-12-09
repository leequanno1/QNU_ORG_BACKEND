package com.qn_org.backend.controllers.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpandUserInfo {
    private String displayName;
    private String fullName;
    private String depName;
    private String majorName;
    private String phoneNumber;
    private int userType;
    private String email;
}
