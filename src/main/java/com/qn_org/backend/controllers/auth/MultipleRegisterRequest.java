package com.qn_org.backend.controllers.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MultipleRegisterRequest {
    private List<RequestStudentInfo> studentInfos;
    private List<RequestStaffInfo> staffInfos;
}
