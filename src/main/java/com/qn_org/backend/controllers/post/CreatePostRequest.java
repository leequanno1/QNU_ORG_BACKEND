package com.qn_org.backend.controllers.post;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePostRequest {
    private String posterId;

    private String postTitle;

    private String postContent;

    private List<MultipartFile> images;
}
