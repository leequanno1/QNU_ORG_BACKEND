package com.qn_org.backend.controllers.member;

import lombok.Data;

import java.util.List;

@Data
public class GetReviewMemberRequest {
    private List<String> userIds;
    private String orgId;
}
