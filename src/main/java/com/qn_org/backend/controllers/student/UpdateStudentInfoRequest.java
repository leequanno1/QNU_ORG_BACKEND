package com.qn_org.backend.controllers.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateStudentInfoRequest {
    private String studentKey;

    private String majorId;

    private String firstName;

    private String lastName;

    private String fullName;

    private String phoneNumber;
}
