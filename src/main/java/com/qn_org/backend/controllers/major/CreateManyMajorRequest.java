package com.qn_org.backend.controllers.major;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateManyMajorRequest {
    private List<CreateMajorRequest> majorInfo;
}
