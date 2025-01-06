package com.qn_org.backend.controllers.department;

import com.qn_org.backend.models.Department;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DepartmentDTO {
    private String departmentId;

    private String depName;

    private int majors = 0;

    private boolean delFlg = false;

    private Date insDate = new Date();

    public DepartmentDTO(Department dep) {
        this.departmentId = dep.getDepartmentId();
        this.depName = dep.getDepName();
        this.majors = dep.getMajors();
        this.delFlg = dep.isDelFlg();
        this.insDate = dep.getInsDate();
    }
}
