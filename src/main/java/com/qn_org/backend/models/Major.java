package com.qn_org.backend.models;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

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
    private Date insDate = new Date();

}
