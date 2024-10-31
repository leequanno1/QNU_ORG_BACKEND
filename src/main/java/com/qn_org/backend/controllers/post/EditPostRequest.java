package com.qn_org.backend.controllers.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.aop.framework.AopInfrastructureBean;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditPostRequest {
    private String postId;

    private String posterId;

    private String postTitle;

    private String postContent;
}
