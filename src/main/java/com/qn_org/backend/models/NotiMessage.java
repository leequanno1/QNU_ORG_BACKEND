package com.qn_org.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NotiMessage {
    private String userId;
    private String orgId;
}
