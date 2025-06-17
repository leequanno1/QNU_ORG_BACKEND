package com.qn_org.backend.controllers.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeletePostRequest {
    private String postId;
    private String posterId;
}
