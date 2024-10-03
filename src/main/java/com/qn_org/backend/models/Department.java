package com.qn_org.backend.models;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "DEPARTMENT")
public class Department {

    @Id
    @Column(name = "DEPARTMENT_ID", nullable = false, unique = true)
    private String departmentId;

    @Column(name = "DEP_NAME")
    private String depName;

    @Column(name = "DEL_FLG", nullable = false)
    private boolean delFlg = false;

    @Column(name = "INS_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;

    public String getDepartmentId() {
        return departmentId;
    }

    public String getDepName() {
        return depName;
    }

    public boolean isDelFlg() {
        return delFlg;
    }

    public Date getInsDate() {
        return insDate;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public void setDelFlg(boolean delFlg) {
        this.delFlg = delFlg;
    }

    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }
}
