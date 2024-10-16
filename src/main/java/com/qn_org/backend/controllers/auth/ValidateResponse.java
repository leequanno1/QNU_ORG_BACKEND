package com.qn_org.backend.controllers.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ValidateResponse {
    private boolean isValidated;
    private String problemCause;
}
