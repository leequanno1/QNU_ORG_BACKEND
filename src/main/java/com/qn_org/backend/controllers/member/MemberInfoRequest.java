package com.qn_org.backend.controllers.member;

import lombok.Data;

import java.util.List;

@Data
public class MemberInfoRequest {
    private List<String> memberIds;
}
