package com.qn_org.backend.controllers.major;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data()
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MajorExtend {
    private String majorId;

    private String departmentId;

    private String majorName;

    private String depName;

    private boolean delFlg;

    private Date insDate;
}
