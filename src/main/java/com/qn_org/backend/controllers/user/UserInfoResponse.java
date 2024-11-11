package com.qn_org.backend.controllers.user;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private String userId;
    private String emailAddress;
    private String displayName;
    private String userAvatar;
    private int userType;
}
