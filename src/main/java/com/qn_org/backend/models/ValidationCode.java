package com.qn_org.backend.models;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "VALIDATION_CODE")
public class ValidationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VARLIDATION_ID", nullable = false, unique = true)
    private int validationId;

    @Column(name = "VALIDATION_EMAIL", nullable = false)
    private String validationEmail;

    @Column(name = "VALIDATION_CODE", nullable = false)
    private String validationCode;

    @Column(name = "USER_TYPE", nullable = false)
    private int userType;

    @Column(name = "INS_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;

    @Column(name = "DEL_FLG", nullable = false)
    private boolean delFlg = false;

    public int getValidationId() {
        return validationId;
    }

    public String getValidationEmail() {
        return validationEmail;
    }

    public String getValidationCode() {
        return validationCode;
    }

    public int getUserType() {
        return userType;
    }

    public Date getInsDate() {
        return insDate;
    }

    public boolean isDelFlg() {
        return delFlg;
    }

    public void setValidationId(int validationId) {
        this.validationId = validationId;
    }

    public void setValidationEmail(String validationEmail) {
        this.validationEmail = validationEmail;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }

    public void setDelFlg(boolean delFlg) {
        this.delFlg = delFlg;
    }
}
