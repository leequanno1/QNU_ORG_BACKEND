package com.qn_org.backend.controllers.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordRequest {
    private String userId;
    private String validationCode;
    private String newPassword;
    private String repeatPassword;
}
