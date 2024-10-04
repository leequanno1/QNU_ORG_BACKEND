package com.qn_org.backend.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@Entity
@RequiredArgsConstructor
@Table(name = "STAFF_INFO")
public class StaffInfo {

    @Id
    @Column(name = "STAFF_KEY", nullable = false, unique = true)
    private String staffKey;

    @ManyToOne
    @JoinColumn(name = "DEP_ID")
    private Department department;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "IS_TEACHER", nullable = false)
    private boolean isTeacher = true;

    @Column(name = "INS_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;

    @Column(name = "DEL_FLG", nullable = false)
    private boolean delFlg = false;
}
