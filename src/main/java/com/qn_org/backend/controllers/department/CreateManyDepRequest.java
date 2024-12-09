package com.qn_org.backend.controllers.department;

import lombok.Data;

import java.util.List;

@Data
public class CreateManyDepRequest {
    private List<String> depNames;
}
