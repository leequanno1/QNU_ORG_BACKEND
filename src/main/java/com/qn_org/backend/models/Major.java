package com.qn_org.backend.models;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@Entity
@RequiredArgsConstructor
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

}
