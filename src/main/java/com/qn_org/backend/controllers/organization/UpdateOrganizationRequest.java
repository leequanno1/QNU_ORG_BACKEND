package com.qn_org.backend.controllers.organization;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateOrganizationRequest {
    private String orgId;
    @Nullable()
    private String orgName;
    @Nullable()
    private String orgDescription;
    @Nullable
    private MultipartFile orgAvatar;
    @Nullable
    private MultipartFile orgBackGround;
}
