package com.qn_org.backend.controllers.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInfoRequest {
    private String displayName;
    private MultipartFile userAvatar;
    private MultipartFile userBackground;
}
