package com.qn_org.backend.controllers.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestStudentInfo {
    private String userId;

    private String emailAddress;

    private String displayName;

    private int userType;

    private String studentKey;

    private String majorId;

    private String firstName;

    private String lastName;

    private String fullName;

    private String phoneNumber;
}
