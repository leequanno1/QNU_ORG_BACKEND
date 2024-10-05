package com.qn_org.backend.responses;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class QnuResponseEntity<T> extends ResponseEntity<ApiResponse<T>> {

    public QnuResponseEntity(HttpStatusCode status) {
        super(status);
    }

    public QnuResponseEntity(ApiResponse<T> body, HttpStatusCode status) {
        super(body, status);
    }

    public QnuResponseEntity(T body, HttpStatusCode status) {
        super(new ApiResponse<>(status.value(),body), status);
    }

    public QnuResponseEntity(MultiValueMap<String, String> headers, HttpStatusCode status) {
        super(headers, status);
    }

    public QnuResponseEntity(ApiResponse<T> body, MultiValueMap<String, String> headers, int rawStatus) {
        super(body, headers, rawStatus);
    }

    public QnuResponseEntity(T body, MultiValueMap<String, String> headers, int rawStatus) {
        super(new ApiResponse<>(rawStatus,body), headers, rawStatus);
    }

    public QnuResponseEntity(ApiResponse<T> body, MultiValueMap<String, String> headers, HttpStatusCode statusCode) {
        super(body, headers, statusCode);
    }

    public QnuResponseEntity(T body, MultiValueMap<String, String> headers, HttpStatusCode statusCode) {
        super(new ApiResponse<>(statusCode.value(),body), headers, statusCode);
    }
}
