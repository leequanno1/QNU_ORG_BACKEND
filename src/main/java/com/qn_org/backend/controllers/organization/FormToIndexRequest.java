package com.qn_org.backend.controllers.organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormToIndexRequest {
    private int from;
    private int to;
}
