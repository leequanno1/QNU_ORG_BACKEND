package com.qn_org.backend.controllers.organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrganizationRequest {
    private String orgName;
    private String orgDescription;
    private MultipartFile orgAvatar;
    private MultipartFile orgBackGround;
}
