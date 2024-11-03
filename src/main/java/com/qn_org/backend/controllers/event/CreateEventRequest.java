package com.qn_org.backend.controllers.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventRequest {
    private Date begin;

    private Date end;

    private String hosterId;

    private String eventName;

    private String eventDescription;

    private List<MultipartFile> images;
}
