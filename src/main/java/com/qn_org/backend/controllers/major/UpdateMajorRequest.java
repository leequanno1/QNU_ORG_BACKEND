package com.qn_org.backend.controllers.major;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMajorRequest {
    private String majorId;
    private String depId;
    private String majorName;
}
