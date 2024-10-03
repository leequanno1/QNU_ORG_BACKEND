package com.qn_org.backend.models;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "STUDENT_INFO")
public class StudentInfo {

    @Id
    @Column(name = "STUDENT_KEY", nullable = false, unique = true)
    private String studentKey;

    @ManyToOne
    @JoinColumn(name = "MAJOR_ID", nullable = false)
    private Major major;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "INS_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;

    @Column(name = "DEL_FLG", nullable = false)
    private boolean delFlg = false;

    public String getStudentKey() {
        return studentKey;
    }

    public Major getMajor() {
        return major;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getInsDate() {
        return insDate;
    }

    public boolean isDelFlg() {
        return delFlg;
    }

    public void setStudentKey(String studentKey) {
        this.studentKey = studentKey;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }

    public void setDelFlg(boolean delFlg) {
        this.delFlg = delFlg;
    }
}
