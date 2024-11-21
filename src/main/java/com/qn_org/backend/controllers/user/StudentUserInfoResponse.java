package com.qn_org.backend.controllers.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentUserInfoResponse {
    private String userId;
    private String displayName;
    private String fullName;
    private String userAvatar;
    private String userBackground;
    private String majorId;
    private String majorName;
    private String departmentId;
    private String depName;
    private String courseNumber;
    private String phoneNumber;

    public  StudentUserInfoResponse(
            String userId,
            String displayName,
            String fullName,
            String userAvatar,
            String userBackground,
            String majorId,
            String majorName,
            String departmentId,
            String depName,
            String phoneNumber
    ) {
        this.userId = userId;
        this.displayName = displayName;
        this.fullName = fullName;
        this.userAvatar = userAvatar;
        this.userBackground = userBackground;
        this.majorId = majorId;
        this.majorName = majorName;
        this.departmentId = departmentId;
        this.depName = depName;
        this.courseNumber = this.userId.substring(0,2);
        this.phoneNumber = phoneNumber;
    }
}
