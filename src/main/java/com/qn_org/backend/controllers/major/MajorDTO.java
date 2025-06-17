package com.qn_org.backend.controllers.major;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qn_org.backend.models.Department;
import com.qn_org.backend.models.Major;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MajorDTO {
    private String majorId;

    private String departmentId;

    private String majorName;

    private boolean delFlg;

    private Date insDate;

    public MajorDTO(Major major) {
        this.majorId = major.getMajorId();
        this.departmentId = major.getDepartment().getDepartmentId();
        this.majorName = major.getMajorName();
        this.delFlg = major.isDelFlg();
        this.insDate = major.getInsDate();
    }

    public static List<MajorDTO> fromList(List<Major> majors) {
        List<MajorDTO> dto = new ArrayList<>();
        for(Major major: majors) {
            dto.add(new MajorDTO(major));
        }
        return dto;
    }
}
