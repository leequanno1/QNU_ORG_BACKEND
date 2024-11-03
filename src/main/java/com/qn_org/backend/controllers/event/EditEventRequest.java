package com.qn_org.backend.controllers.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EditEventRequest {
    private String eventId;

    private Date begin;

    private Date end;

    private String hosterId;

    private String eventName;

    private String eventDescription;

    private List<String> delImagesId;

    private List<MultipartFile> newImage;
}
