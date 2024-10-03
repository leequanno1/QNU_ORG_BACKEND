package com.qn_org.backend.models;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MAJOR")
public class Major {

    @Id
    @Column(name = "MAJOR_ID", nullable = false, unique = true)
    private String majorId;

    @ManyToOne
    @JoinColumn(name = "DEP_ID", nullable = false)
    private Department department;

    @Column(name = "MAJOR_NAME")
    private String majorName;

    @Column(name = "DEL_FLG", nullable = false)
    private boolean delFlg = true;

    @Column(name = "INS_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;

    public String getMajorId() {
        return majorId;
    }

    public Department getDepartment() {
        return department;
    }

    public String getMajorName() {
        return majorName;
    }

    public boolean isDelFlg() {
        return delFlg;
    }

    public Date getInsDate() {
        return insDate;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public void setDelFlg(boolean delFlg) {
        this.delFlg = delFlg;
    }

    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }
}
