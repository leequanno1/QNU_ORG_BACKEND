package com.qn_org.backend.controllers.staff;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qn_org.backend.models.StaffInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StaffInfoDTO {
    private String staffKey;

    private String departmentId;

    private String firstName;

    private String lastName;

    private String fullName;

    private String phoneNumber;

    private boolean isTeacher;

    private Date insDate;

    private boolean delFlg;

    public StaffInfoDTO(StaffInfo info) {
        this.staffKey = info.getStaffKey();
        this.departmentId = info.getDepartment().getDepartmentId();
        this.firstName = info.getFirstName();
        this.lastName = info.getLastName();
        this.fullName = info.getFullName();
        this.phoneNumber = info.getPhoneNumber();
        this.isTeacher = info.isTeacher();
        this.insDate = info.getInsDate();
        this.delFlg = info.isDelFlg();
    }

    public static List<StaffInfoDTO> fromList(List<StaffInfo> infos) {
        List<StaffInfoDTO> dtos = new ArrayList<>();
        for(var info: infos) {
            dtos.add(new StaffInfoDTO(info));
        }
        return dtos;
    }
}
