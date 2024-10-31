package com.qn_org.backend.common_requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FromToIndexRequest {
    private int from;
    private int to;

    public boolean isValid() {
        return this.getTo() - this.getFrom() >= 0;
    }
    public int getLimit() {
        return this.getTo() - this.getFrom() + 1;
    }

    public int getOffset() {
        return this.getFrom();
    }
}
