package com.qn_org.backend.controllers.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteEventRequest {
    private String eventId;
    private String hosterId;
}
