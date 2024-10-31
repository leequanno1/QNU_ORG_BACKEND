package com.qn_org.backend.controllers.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestStaffInfo {
    private String staffKey;

    private String depId;

    private String firstName;

    private String lastName;

    private String fullName;

    private String phoneNumber;

    private boolean isTeacher;
}
