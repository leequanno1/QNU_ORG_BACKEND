package com.qn_org.backend.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ApiResponse<T> {
    private int status;
    private T data;

    public ApiResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }
}
