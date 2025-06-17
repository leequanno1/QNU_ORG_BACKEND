package com.qn_org.backend.controllers.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qn_org.backend.models.StudentInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentInfoDTO {
    private String studentKey;

    private String majorId;

    private String firstName;

    private String lastName;

    private String fullName;

    private String phoneNumber;

    private Date insDate;

    private boolean delFlg;

    public StudentInfoDTO(StudentInfo info) {
        this.studentKey = info.getStudentKey();
        this.majorId = info.getMajor().getMajorId();
        this.firstName = info.getFirstName();
        this.lastName = info.getLastName();
        this.fullName = info.getFullName();
        this.phoneNumber = info.getPhoneNumber();
        this.insDate = info.getInsDate();
        this.delFlg = info.isDelFlg();
    }

    public static List<StudentInfoDTO> fromList(List<StudentInfo> infos) {
        List<StudentInfoDTO> dtos = new ArrayList<>();
        for(var info : infos) {
            dtos.add(new StudentInfoDTO(info));
        }
        return dtos;
    }

}
