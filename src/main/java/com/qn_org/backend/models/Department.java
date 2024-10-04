package com.qn_org.backend.models;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@Entity
@RequiredArgsConstructor
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

}
