package com.qn_org.backend.models;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@Entity
@RequiredArgsConstructor
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

}
