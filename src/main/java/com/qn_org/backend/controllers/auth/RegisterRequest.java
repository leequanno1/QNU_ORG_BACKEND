package com.qn_org.backend.controllers.auth;
import com.qn_org.backend.controllers.student.StudentInfoService;
import com.qn_org.backend.models.StaffInfo;
import com.qn_org.backend.models.enums.UserType;
import com.qn_org.backend.repositories.StaffRepository;
import com.qn_org.backend.repositories.StudentRepository;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    public String userId;
    public String password;
    public String email;
    public String displayName;
    public int userType;

    public String handleGetUserInfoKey() {
        String startStaff = "STA_";
        String startTeacher = "TEA_";
        String startStudent = "STU_";
        String id = UUID.randomUUID().toString();
        UserType typeEnum = UserType.fromValue(userType);
        switch (typeEnum){
            case STAFF -> {
                return startStaff+id;
            }
            case TEACHER -> {
                return startTeacher+id;
            }
            case STUDENT -> {
                return startStudent+id;
            }
            default -> {
                return null;
            }
        }
    }

}